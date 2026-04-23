package com.answufeng.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build

/**
 * 启动服务；Android 8.0+ 若服务需尽快进入前台，请使用 [startForegroundServiceCompat]。
 */
inline fun <reified T : Service> Context.startServiceCompat(noinline configure: Intent.() -> Unit = {}) {
    startService(Intent(this, T::class.java).apply(configure))
}

/**
 * 启动前台服务（O+ 走 [Context.startForegroundService]），并在低版本回退为 [Context.startService]。
 */
inline fun <reified T : Service> Context.startForegroundServiceCompat(
    noinline configure: Intent.() -> Unit = {},
) {
    val intent = Intent(this, T::class.java).apply(configure)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(intent)
    } else {
        @Suppress("DEPRECATION")
        startService(intent)
    }
}

/** 停止指定类型服务。 */
inline fun <reified T : Service> Context.stopServiceCompat() {
    stopService(Intent(this, T::class.java))
}
