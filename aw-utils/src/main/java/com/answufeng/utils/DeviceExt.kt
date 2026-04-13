package com.answufeng.utils

import android.os.Build

val deviceBrand: String get() = Build.BRAND

val deviceModel: String get() = Build.MODEL

val deviceManufacturer: String get() = Build.MANUFACTURER

val osVersion: String get() = Build.VERSION.RELEASE

val sdkVersion: Int get() = Build.VERSION.SDK_INT

/**
 * 获取完整的设备信息摘要（适合日志和错误上报）
 *
 * ```kotlin
 * AwLog.i("Device", deviceSummary())
 * // "Xiaomi Mi 14 | Android 14 (SDK 34)"
 * ```
 *
 * @return 格式化的设备信息字符串
 */
fun deviceSummary(): String {
    return "$deviceBrand $deviceModel | Android $osVersion (SDK $sdkVersion)"
}
