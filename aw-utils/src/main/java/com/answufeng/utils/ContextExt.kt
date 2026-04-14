package com.answufeng.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

@Deprecated(
    message = "Use Number.dpToPx(context) for correctness or Number.dp for simplicity",
    replaceWith = ReplaceWith("dp.dpToPx(this)")
)
fun Context.dp2px(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

@Deprecated(
    message = "Use Number.spToPx(context) for correctness or Number.sp for simplicity",
    replaceWith = ReplaceWith("sp.spToPx(this)")
)
fun Context.sp2px(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
}

@Deprecated(
    message = "Use Int.pxToDp(context) instead",
    replaceWith = ReplaceWith("px.pxToDp(this)")
)
fun Context.px2dp(px: Int): Float {
    return px / resources.displayMetrics.density
}

@Deprecated(
    message = "Use Int.pxToSp(context) instead",
    replaceWith = ReplaceWith("px.pxToSp(this)")
)
@Suppress("DEPRECATION")
fun Context.px2sp(px: Int): Float {
    return px / resources.displayMetrics.scaledDensity
}

/**
 * 将 dp 值转换为 px（四舍五入）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确，推荐使用 [dpToPx]。
 */
@AwExperimentalApi
val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

/**
 * 将 dp 值转换为 px（浮点数）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确。
 */
@AwExperimentalApi
val Number.dpF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.density

/**
 * 将 sp 值转换为 px（四舍五入）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确，推荐使用 [spToPx]。
 */
@Suppress("DEPRECATION")
@AwExperimentalApi
val Number.sp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

/**
 * 将 sp 值转换为 px（浮点数）。
 *
 * 使用 `Resources.getSystem()`，不依赖 Context。
 * 在多窗口/折叠屏场景下可能不准确。
 */
@Suppress("DEPRECATION")
@AwExperimentalApi
val Number.spF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.scaledDensity

/**
 * 将 dp 值转换为 px（Context 感知，推荐使用）。
 *
 * 使用当前 Context 的 DisplayMetrics，多窗口/折叠屏安全。
 */
fun Number.dpToPx(context: Context): Int =
    (toFloat() * context.resources.displayMetrics.density + 0.5f).toInt()

/**
 * 将 sp 值转换为 px（Context 感知，推荐使用）。
 *
 * 使用当前 Context 的 DisplayMetrics，多窗口/折叠屏安全。
 */
@Suppress("DEPRECATION")
fun Number.spToPx(context: Context): Int =
    (toFloat() * context.resources.displayMetrics.scaledDensity + 0.5f).toInt()

/**
 * 将 px 值转换为 dp。
 */
fun Int.pxToDp(context: Context): Float =
    this / context.resources.displayMetrics.density

/**
 * 将 px 值转换为 sp。
 */
@Suppress("DEPRECATION")
fun Int.pxToSp(context: Context): Float =
    this / context.resources.displayMetrics.scaledDensity

/** 屏幕宽度（px）。 */
val Context.screenWidth: Int get() = resources.displayMetrics.widthPixels

/** 屏幕高度（px）。 */
val Context.screenHeight: Int get() = resources.displayMetrics.heightPixels

/** 状态栏高度（px）。 */
val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

/** 导航栏高度（px）。 */
val Context.navigationBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
