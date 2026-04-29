package com.answufeng.utils

import android.app.Activity
import android.graphics.Color
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * 设置状态栏背景色与浅色/深色前景（图标与系统装饰对比度）。
 *
 * @param color 状态栏颜色（通常为不透明色；完全沉浸式需配合 `Window` 标志另行处理）
 * @param darkStatusBarIcons `true` 表示状态栏图标为深色（适合浅色背景）；`false` 为浅色图标（适合深色背景）
 */
fun Activity.setStatusBarColorAndStyle(color: Int, darkStatusBarIcons: Boolean) {
    window.statusBarColor = color
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.isAppearanceLightStatusBars = darkStatusBarIcons
}

/**
 * 设置导航栏背景色与浅色/深色导航栏按钮（手势条/三键区域对比度）。
 *
 * @param color 导航栏颜色
 * @param darkNavigationBarIcons `true` 表示导航栏按钮为深色（适合浅色背景）；`false` 为浅色按钮（适合深色背景）
 */
fun Activity.setNavigationBarColorAndStyle(color: Int, darkNavigationBarIcons: Boolean) {
    window.navigationBarColor = color
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.isAppearanceLightNavigationBars = darkNavigationBarIcons
}

/**
 * 透明状态栏（沉浸式）。
 *
 * 使状态栏背景透明，内容延伸到状态栏下方。
 * 需要在布局中处理 padding 以避免内容被遮挡。
 */
fun Activity.transparentStatusBar() {
    window.statusBarColor = Color.TRANSPARENT
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

/**
 * 透明导航栏（沉浸式）。
 *
 * 使导航栏背景透明，内容延伸到导航栏下方。
 * 需要在布局中处理 padding 以避免内容被遮挡。
 */
fun Activity.transparentNavBar() {
    window.navigationBarColor = Color.TRANSPARENT
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

/**
 * 同时透明状态栏和导航栏（全沉浸式）。
 */
fun Activity.transparentSystemBars() {
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

/**
 * 判断状态栏是否可见。
 *
 * 通过检测状态栏 insets 高度判断：高度 > 0 表示状态栏存在且可见。
 */
fun Activity.isStatusBarVisible(): Boolean {
    val insets = window.decorView.rootWindowInsets ?: return true
    val statusBarInsets = WindowInsetsCompat.toWindowInsetsCompat(insets)
        .getInsets(WindowInsetsCompat.Type.statusBars())
    return statusBarInsets.top > 0
}

/**
 * 判断导航栏是否可见。
 *
 * 通过检测导航栏 insets 高度判断：高度 > 0 表示导航栏存在且可见。
 */
fun Activity.isNavBarVisible(): Boolean {
    val insets = window.decorView.rootWindowInsets ?: return true
    val navInsets = WindowInsetsCompat.toWindowInsetsCompat(insets)
        .getInsets(WindowInsetsCompat.Type.navigationBars())
    return navInsets.bottom > 0
}

/**
 * 隐藏状态栏。
 */
fun Activity.hideStatusBar() {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.hide(WindowInsetsCompat.Type.statusBars())
}

/**
 * 显示状态栏。
 */
fun Activity.showStatusBar() {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.show(WindowInsetsCompat.Type.statusBars())
}

/**
 * 隐藏导航栏。
 */
fun Activity.hideNavBar() {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.hide(WindowInsetsCompat.Type.navigationBars())
}

/**
 * 显示导航栏。
 */
fun Activity.showNavBar() {
    val controller = WindowCompat.getInsetsController(window, window.decorView)
    controller.show(WindowInsetsCompat.Type.navigationBars())
}
