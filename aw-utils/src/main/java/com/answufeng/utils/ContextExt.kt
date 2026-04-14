package com.answufeng.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * dp 转 px。
 */
fun Context.dp2px(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

/**
 * sp 转 px。
 */
fun Context.sp2px(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
}

/**
 * px 转 dp。
 */
fun Context.px2dp(px: Int): Float {
    return px / resources.displayMetrics.density
}

/**
 * px 转 sp。
 */
@Suppress("DEPRECATION")
fun Context.px2sp(px: Int): Float {
    return px / resources.displayMetrics.scaledDensity
}

/**
 * 将 dp 值转换为 px（四舍五入）。
 *
 * 不需要 Context，使用系统默认 DisplayMetrics。
 */
val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

/**
 * 将 dp 值转换为 px（浮点数）。
 */
val Number.dpF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.density

/**
 * 将 sp 值转换为 px（四舍五入）。
 */
@Suppress("DEPRECATION")
val Number.sp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

/**
 * 将 sp 值转换为 px（浮点数）。
 */
@Suppress("DEPRECATION")
val Number.spF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.scaledDensity

/**
 * 屏幕宽度（px）。
 */
val Context.screenWidth: Int get() = resources.displayMetrics.widthPixels

/**
 * 屏幕高度（px）。
 */
val Context.screenHeight: Int get() = resources.displayMetrics.heightPixels

/**
 * 状态栏高度（px）。
 */
val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

/**
 * 导航栏高度（px）。
 */
val Context.navigationBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
