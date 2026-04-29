package com.answufeng.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/** 网络类型枚举。 */
enum class NetworkType {
    /** Wi-Fi 网络连接。 */
    WIFI,
    /** 移动数据网络连接（蜂窝网络）。 */
    CELLULAR,
    /** 以太网有线连接。 */
    ETHERNET,
    /** 无可用网络连接。 */
    NONE
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
private fun Context.getActiveNetworkCapabilities(): NetworkCapabilities? {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return null
    return cm.getNetworkCapabilities(network)
}

/**
 * 判断当前网络是否可用（已连接且通过验证）。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkAvailable(): Boolean {
    val caps = getActiveNetworkCapabilities() ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}

/**
 * 判断当前是否连接 Wi-Fi。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isWifiConnected(): Boolean {
    val caps = getActiveNetworkCapabilities() ?: return false
    return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

/**
 * 判断当前是否连接移动数据。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isMobileDataConnected(): Boolean {
    val caps = getActiveNetworkCapabilities() ?: return false
    return caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}

/**
 * 获取当前网络类型。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 *
 * @return [NetworkType] 枚举值
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.getNetworkType(): NetworkType {
    val caps = getActiveNetworkCapabilities() ?: return NetworkType.NONE
    return when {
        caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
        caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
        caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
        else -> NetworkType.NONE
    }
}

/**
 * 判断当前网络是否为指定类型。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 *
 * ```kotlin
 * context.isNetworkType(NetworkType.WIFI)
 * ```
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkType(type: NetworkType): Boolean {
    return getNetworkType() == type
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Deprecated("Use getNetworkType() instead", ReplaceWith("getNetworkType().name"))
fun Context.getNetworkTypeName(): String = getNetworkType().name

/**
 * 观察网络连接状态变化，返回 Flow。
 *
 * 与 [isNetworkAvailable] 一致：已连接且具备 **INTERNET** 与 **VALIDATED** 能力时发射 `true`。
 * 首次订阅时发射当前网络状态。
 *
 * **弱网 / 切换网络**：部分机型在 `onAvailable` 后短时间内 `VALIDATED` 尚未就绪，可能出现短暂 `false`，
 * 随后随 [ConnectivityManager.NetworkCallback.onCapabilitiesChanged] 变为 `true`；UI 层可配合防抖或 Snackbar，避免误判。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 *
 * ```kotlin
 * lifecycleScope.launch {
 *     observeNetworkState().collect { available ->
 *         updateUI(available)
 *     }
 * }
 * ```
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@AwExperimentalApi
fun Context.observeNetworkState(): Flow<Boolean> = callbackFlow {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            trySend(isNetworkAvailable())
        }
        override fun onLost(network: Network) {
            trySend(isNetworkAvailable())
        }
        override fun onUnavailable() {
            trySend(isNetworkAvailable())
        }
        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            trySend(isNetworkAvailable())
        }
    }
    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
    cm.registerNetworkCallback(request, callback)
    trySend(isNetworkAvailable())
    awaitClose { cm.unregisterNetworkCallback(callback) }
}.distinctUntilChanged()

/**
 * 获取当前连接的 Wi-Fi SSID。
 *
 * Android 12+ 需要持有 `ACCESS_FINE_LOCATION` 权限才能获取 SSID，
 * 否则返回 `"<unknown ssid>"`。
 * Android 13+ 需要持有 `NEARBY_WIFI_DEVICES` 权限（或 `ACCESS_FINE_LOCATION`）。
 *
 * 未连接 Wi-Fi 时返回 `null`。
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.getWifiSSID(): String? {
    if (!isWifiConnected()) return null
    return try {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return null
        val info = cm.getNetworkInfo(network) ?: return null
        info.extraInfo?.removeSurrounding("\"")
    } catch (_: Exception) {
        null
    }
}
