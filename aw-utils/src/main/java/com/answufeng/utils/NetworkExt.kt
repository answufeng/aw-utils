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

enum class NetworkType {
    WIFI,
    CELLULAR,
    ETHERNET,
    NONE
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isWifiConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isMobileDataConnected(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val caps = cm.getNetworkCapabilities(network) ?: return false
    return caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}

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

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Deprecated("Use getNetworkType() instead", ReplaceWith("getNetworkType().name"))
fun Context.getNetworkTypeName(): String = getNetworkType().name

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
