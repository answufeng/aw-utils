package com.answufeng.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**
 * 设置 ImageView 的 tint 颜色。
 */
fun ImageView.setTint(color: Int) {
    drawable?.mutate()?.setTint(color)
}

/**
 * 设置 ImageView 的 tint 颜色（资源 ID）。
 */
fun ImageView.setTintRes(@ColorRes colorRes: Int) {
    setTint(context.getColor(colorRes))
}

/**
 * 清除 ImageView 的 tint。
 */
fun ImageView.clearTint() {
    drawable?.mutate()?.setTintList(null)
}

/**
 * 设置 ImageView 图片资源并应用 tint。
 */
fun ImageView.setImageWithTint(@DrawableRes resId: Int, tintColor: Int) {
    setImageResource(resId)
    setTint(tintColor)
}
