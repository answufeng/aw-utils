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
