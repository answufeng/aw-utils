@file:Suppress("unused")

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

/**
 * 网络类型枚举。
 */
enum class NetworkType {
    WIFI,
    CELLULAR,
    ETHERNET,
    NONE
}

/**
 * 判断当前网络是否可用（已连接且通过验证）。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
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
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

/**
 * 判断当前是否连接移动数据。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isMobileDataConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
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
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return NetworkType.NONE
    val caps = cm.getNetworkCapabilities(network) ?: return NetworkType.NONE
    return when {
        caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
        caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CELLULAR
        caps.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
        else -> NetworkType.NONE
    }
}

/**
 * 获取当前网络类型名称。
 *
 * 需要 `ACCESS_NETWORK_STATE` 权限。
 *
 * @deprecated 使用 [getNetworkType] 替代
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Deprecated("Use getNetworkType() instead", ReplaceWith("getNetworkType().name"))
fun Context.getNetworkTypeName(): String = getNetworkType().name

/**
 * 观察网络连接状态变化，返回 Flow。
 *
 * 网络可用时发射 `true`，断开时发射 `false`。
 * 首次订阅时发射当前网络状态。
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
fun Context.observeNetworkState(): Flow<Boolean> = callbackFlow {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworks = mutableSetOf<Network>()
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            activeNetworks.add(network)
            trySend(true)
        }
        override fun onLost(network: Network) {
            activeNetworks.remove(network)
            trySend(activeNetworks.isNotEmpty())
        }
        override fun onUnavailable() {
            trySend(activeNetworks.isNotEmpty())
        }
    }
    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
    cm.registerNetworkCallback(request, callback)
    trySend(isNetworkAvailable())
    awaitClose { cm.unregisterNetworkCallback(callback) }
}.distinctUntilChanged()
