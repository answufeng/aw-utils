package com.answufeng.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.TextViewCompat

/**
 * 设置 TextView 起始侧的 compound drawable（RTL 下为右侧）。
 *
 * @param drawable Drawable 对象，null 则清除
 * @param boundsWidth 显示宽度（像素），0 使用 drawable 原始宽度
 * @param boundsHeight 显示高度（像素），0 使用 drawable 原始高度
 */
fun TextView.setDrawableStart(
    drawable: Drawable?,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawables(start = drawable, boundsWidth = boundsWidth, boundsHeight = boundsHeight)
}

/**
 * 设置 TextView 结束侧的 compound drawable（RTL 下为左侧）。
 */
fun TextView.setDrawableEnd(
    drawable: Drawable?,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawables(end = drawable, boundsWidth = boundsWidth, boundsHeight = boundsHeight)
}

/**
 * 设置 TextView 的顶部 drawable。
 */
fun TextView.setDrawableTop(
    drawable: Drawable?,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawables(top = drawable, boundsWidth = boundsWidth, boundsHeight = boundsHeight)
}

/**
 * 设置 TextView 的底部 drawable。
 */
fun TextView.setDrawableBottom(
    drawable: Drawable?,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawables(bottom = drawable, boundsWidth = boundsWidth, boundsHeight = boundsHeight)
}

/**
 * 设置 TextView 起始侧 drawable（资源 ID）。
 */
fun TextView.setDrawableStartRes(
    @DrawableRes resId: Int,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawableStart(context.getDrawableCompat(resId), boundsWidth, boundsHeight)
}

/**
 * 设置 TextView 结束侧 drawable（资源 ID）。
 */
fun TextView.setDrawableEndRes(
    @DrawableRes resId: Int,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawableEnd(context.getDrawableCompat(resId), boundsWidth, boundsHeight)
}

/**
 * 设置 TextView 的顶部 drawable（资源 ID）。
 */
fun TextView.setDrawableTopRes(
    @DrawableRes resId: Int,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawableTop(context.getDrawableCompat(resId), boundsWidth, boundsHeight)
}

/**
 * 设置 TextView 的底部 drawable（资源 ID）。
 */
fun TextView.setDrawableBottomRes(
    @DrawableRes resId: Int,
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    setDrawableBottom(context.getDrawableCompat(resId), boundsWidth, boundsHeight)
}

/**
 * 清除 TextView 的所有 drawable。
 */
fun TextView.clearDrawables() {
    setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0)
}

/**
 * 为 TextView 的 compound drawable 设置 tint 颜色（相对方向，适配 RTL）。
 */
fun TextView.setDrawableTint(@ColorInt color: Int) {
    val drawables = compoundDrawablesRelative
    val tinted = drawables.map { drawable ->
        drawable?.let {
            val wrapped = DrawableCompat.wrap(it.mutate())
            DrawableCompat.setTint(wrapped, color)
            wrapped
        }
    }
    TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
        this, tinted[0], tinted[1], tinted[2], tinted[3]
    )
}

private fun TextView.setDrawables(
    start: Drawable? = compoundDrawablesRelative[0],
    top: Drawable? = compoundDrawablesRelative[1],
    end: Drawable? = compoundDrawablesRelative[2],
    bottom: Drawable? = compoundDrawablesRelative[3],
    boundsWidth: Int = 0,
    boundsHeight: Int = 0
) {
    listOfNotNull(start, top, end, bottom).forEach { drawable ->
        val w = if (boundsWidth > 0) boundsWidth else drawable.intrinsicWidth
        val h = if (boundsHeight > 0) boundsHeight else drawable.intrinsicHeight
        drawable.setBounds(0, 0, w, h)
    }
    setCompoundDrawablesRelative(start, top, end, bottom)
}

private fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable? {
    return androidx.core.content.ContextCompat.getDrawable(this, resId)
}
