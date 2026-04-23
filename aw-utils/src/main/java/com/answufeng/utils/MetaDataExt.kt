package com.answufeng.utils

import android.app.Activity
import android.app.Service
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

/**
 * 读取 [Application] 节点下 `<meta-data android:name="..." android:value="..."/>` 的字符串。
 */
fun Context.getApplicationMetaDataString(key: String): String? {
    return try {
        val ai = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        ai.metaData?.getString(key)
    } catch (_: Exception) {
        null
    }
}

/**
 * 读取 Application `meta-data` 的整型（`android:value` 为 `@integer/...` 或整型资源时）。
 */
fun Context.getApplicationMetaDataInt(key: String, defaultValue: Int = 0): Int {
    return try {
        val ai = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        ai.metaData?.getInt(key, defaultValue) ?: defaultValue
    } catch (_: Exception) {
        defaultValue
    }
}

/**
 * 读取 Application `meta-data` 的布尔（`android:value` 为 `true`/`false`）。
 */
fun Context.getApplicationMetaDataBoolean(key: String, defaultValue: Boolean = false): Boolean {
    return try {
        val ai = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        ai.metaData?.getBoolean(key, defaultValue) ?: defaultValue
    } catch (_: Exception) {
        defaultValue
    }
}

/**
 * 读取当前 [Activity] 在 Manifest 中声明的 `meta-data` 字符串。
 */
fun Activity.getActivityMetaDataString(key: String): String? {
    return try {
        val info = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
        info.metaData?.getString(key)
    } catch (_: Exception) {
        null
    }
}

/**
 * 读取已声明的 [Service] 的 `meta-data` 字符串。
 */
fun <S : Service> Context.getServiceMetaDataString(serviceClass: Class<S>, key: String): String? {
    return try {
        val cn = ComponentName(this, serviceClass)
        val info = packageManager.getServiceInfo(cn, PackageManager.GET_META_DATA)
        info.metaData?.getString(key)
    } catch (_: Exception) {
        null
    }
}

/**
 * 读取已声明的 [BroadcastReceiver] 的 `meta-data` 字符串。
 */
fun <R : BroadcastReceiver> Context.getReceiverMetaDataString(receiverClass: Class<R>, key: String): String? {
    return try {
        val cn = ComponentName(this, receiverClass)
        val info = packageManager.getReceiverInfo(cn, PackageManager.GET_META_DATA)
        info.metaData?.getString(key)
    } catch (_: Exception) {
        null
    }
}

/** @see getServiceMetaDataString */
inline fun <reified S : Service> Context.getServiceMetaDataString(key: String): String? =
    getServiceMetaDataString(S::class.java, key)

/** @see getReceiverMetaDataString */
inline fun <reified R : BroadcastReceiver> Context.getReceiverMetaDataString(key: String): String? =
    getReceiverMetaDataString(R::class.java, key)
