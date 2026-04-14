package com.answufeng.utils

import android.content.Context
import android.os.Build

@Deprecated(
    message = "Use Context extension property instead to avoid namespace pollution",
    replaceWith = ReplaceWith("deviceBrand"),
    level = DeprecationLevel.WARNING
)
val deviceBrand: String get() = Build.BRAND

@Deprecated(
    message = "Use Context extension property instead to avoid namespace pollution",
    replaceWith = ReplaceWith("deviceModel"),
    level = DeprecationLevel.WARNING
)
val deviceModel: String get() = Build.MODEL

@Deprecated(
    message = "Use Context extension property instead to avoid namespace pollution",
    replaceWith = ReplaceWith("deviceManufacturer"),
    level = DeprecationLevel.WARNING
)
val deviceManufacturer: String get() = Build.MANUFACTURER

@Deprecated(
    message = "Use Context extension property instead to avoid namespace pollution",
    replaceWith = ReplaceWith("osVersion"),
    level = DeprecationLevel.WARNING
)
val osVersion: String get() = Build.VERSION.RELEASE

@Deprecated(
    message = "Use Context extension property instead to avoid namespace pollution",
    replaceWith = ReplaceWith("sdkVersion"),
    level = DeprecationLevel.WARNING
)
val sdkVersion: Int get() = Build.VERSION.SDK_INT

@Deprecated(
    message = "Use Context.deviceSummary() instead",
    level = DeprecationLevel.WARNING
)
@Suppress("DEPRECATION")
fun deviceSummary(): String {
    return "$deviceBrand $deviceModel | Android $osVersion (SDK $sdkVersion)"
}

/** 设备品牌（如 "Xiaomi"、"Huawei"、"samsung"）。 */
val Context.deviceBrand: String get() = Build.BRAND

/** 设备型号（如 "Mi 14"、"SM-S928B"）。 */
val Context.deviceModel: String get() = Build.MODEL

/** 设备制造商（如 "Xiaomi"、"samsung"）。 */
val Context.deviceManufacturer: String get() = Build.MANUFACTURER

/** Android 版本号（如 "14"）。 */
val Context.osVersion: String get() = Build.VERSION.RELEASE

/** Android SDK 版本号（如 34）。 */
val Context.sdkVersion: Int get() = Build.VERSION.SDK_INT

/**
 * 获取完整的设备信息摘要（适合日志和错误上报）。
 *
 * ```kotlin
 * AwLog.i("Device", deviceSummary())
 * // "Xiaomi Mi 14 | Android 14 (SDK 34)"
 * ```
 */
@AwExperimentalApi
fun Context.deviceSummary(): String =
    "$deviceBrand $deviceModel | Android $osVersion (SDK $sdkVersion)"
