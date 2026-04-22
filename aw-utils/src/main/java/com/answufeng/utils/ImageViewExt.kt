package com.answufeng.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.widget.ImageViewCompat

/**
 * 设置 ImageView 的 tint 颜色（与 `app:tint` / ImageView 着色行为一致，避免 Drawable 共享副作用）。
 *
 * @param color tint 颜色值
 */
fun ImageView.setTint(color: Int) {
    ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(color))
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
    ImageViewCompat.setImageTintList(this, null)
}

/**
 * 设置 ImageView 图片资源并应用 tint。
 */
fun ImageView.setImageWithTint(@DrawableRes resId: Int, tintColor: Int) {
    setImageResource(resId)
    setTint(tintColor)
}
