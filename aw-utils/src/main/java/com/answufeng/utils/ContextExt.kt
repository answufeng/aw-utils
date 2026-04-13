package com.answufeng.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun Context.dp2px(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}

fun Context.sp2px(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
}

fun Context.px2dp(px: Int): Float {
    return px / resources.displayMetrics.density
}

@Suppress("DEPRECATION")
fun Context.px2sp(px: Int): Float {
    return px / resources.displayMetrics.scaledDensity
}

val Number.dp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Number.dpF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.density

@Suppress("DEPRECATION")
val Number.sp: Int
    get() = (toFloat() * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()

@Suppress("DEPRECATION")
val Number.spF: Float
    get() = toFloat() * Resources.getSystem().displayMetrics.scaledDensity

val Context.screenWidth: Int get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int get() = resources.displayMetrics.heightPixels

val Context.statusBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }

val Context.navigationBarHeight: Int
    get() {
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
    }
