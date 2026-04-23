package com.answufeng.utils

import android.app.Activity
import androidx.core.view.WindowCompat

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
