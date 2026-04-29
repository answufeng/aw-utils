package com.answufeng.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.WindowManager

/**
 * 判断设备是否为平板。
 */
val Context.isTablet: Boolean
    get() = (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) >=
            Configuration.SCREENLAYOUT_SIZE_LARGE

/**
 * 判断是否为横屏。
 */
val Context.isLandscape: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * 判断是否为竖屏。
 */
val Context.isPortrait: Boolean
    get() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

/**
 * 获取屏幕亮度（0-255）。
 */
fun Activity.getWindowBrightness(): Int {
    return window.attributes.screenBrightness.let {
        if (it < 0) {
            try {
                val resolver = contentResolver
                android.provider.Settings.System.getInt(resolver, android.provider.Settings.System.SCREEN_BRIGHTNESS)
            } catch (_: Exception) {
                128
            }
        } else {
            (it * 255).toInt().coerceIn(0, 255)
        }
    }
}

/**
 * 设置当前窗口亮度。
 *
 * @param brightness 亮度值 0-255
 */
fun Activity.setWindowBrightness(brightness: Int) {
    val window = this.window
    val layoutParams = window.attributes
    layoutParams.screenBrightness = brightness.coerceIn(0, 255) / 255f
    window.attributes = layoutParams
}

/**
 * 设置当前窗口亮度跟随系统。
 */
fun Activity.resetWindowBrightness() {
    val window = this.window
    val layoutParams = window.attributes
    layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE
    window.attributes = layoutParams
}

/**
 * 判断屏幕是否常亮。
 */
fun Activity.isScreenKeepOn(): Boolean {
    return window.attributes.flags and WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON != 0
}

/**
 * 设置屏幕常亮。
 */
fun Activity.setScreenKeepOn(keepOn: Boolean) {
    if (keepOn) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    } else {
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}

/**
 * 判断系统是否开启自动亮度调节。
 */
fun Context.isAutoBrightnessEnabled(): Boolean {
    return try {
        android.provider.Settings.System.getInt(
            contentResolver,
            android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE
        ) == android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
    } catch (_: Exception) {
        false
    }
}

/**
 * 开启或关闭系统自动亮度调节。
 *
 * 需要写入设置权限 `android.permission.WRITE_SETTINGS`，
 * 或通过 `Settings.ACTION_MANAGE_WRITE_SETTINGS` 获取授权。
 *
 * @return 是否设置成功
 */
fun Context.setAutoBrightnessEnabled(enabled: Boolean): Boolean {
    return try {
        android.provider.Settings.System.putInt(
            contentResolver,
            android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
            if (enabled) android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            else android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
    } catch (_: Exception) {
        false
    }
}

/**
 * 获取系统屏幕亮度（0-255）。
 *
 * 仅在非自动亮度模式下有意义。
 */
fun Context.getSystemBrightness(): Int {
    return try {
        android.provider.Settings.System.getInt(
            contentResolver,
            android.provider.Settings.System.SCREEN_BRIGHTNESS
        )
    } catch (_: Exception) {
        128
    }
}
