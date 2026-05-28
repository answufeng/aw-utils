package com.answufeng.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * 获取 Drawable 资源（兼容 API 21 以下）。
 */
fun Context.getDrawableCompat(
    @DrawableRes resId: Int,
): Drawable? {
    return ContextCompat.getDrawable(this, resId)
}

/**
 * 获取颜色资源（兼容 API 23 以下）。
 */
fun Context.getColorCompat(
    @ColorRes resId: Int,
): Int {
    return ContextCompat.getColor(this, resId)
}
