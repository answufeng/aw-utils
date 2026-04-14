package com.answufeng.utils

import android.view.View

@Deprecated(
    message = "Use debounceClick() for clearer semantics",
    replaceWith = ReplaceWith("debounceClick(interval, onClick)")
)
fun View.onClick(interval: Long = 500L, onClick: (View) -> Unit) {
    debounceClick(interval, onClick)
}

/**
 * 设置防抖点击监听器。
 *
 * @param interval 防抖间隔（毫秒），默认 500ms，必须 > 0
 * @param onClick 点击回调
 */
inline fun View.debounceClick(interval: Long = 500L, crossinline onClick: (View) -> Unit) {
    require(interval > 0L) { "Debounce interval must be > 0ms, got $interval" }
    var lastClickTime = 0L
    setOnClickListener { v ->
        val now = System.currentTimeMillis()
        if (now - lastClickTime >= interval) {
            lastClickTime = now
            onClick(v)
        }
    }
}

/** 设置 View 可见。 */
fun View.visible() { visibility = View.VISIBLE }

/** 设置 View 不可见（占位）。 */
fun View.invisible() { visibility = View.INVISIBLE }

/** 设置 View 隐藏（不占位）。 */
fun View.gone() { visibility = View.GONE }

/**
 * 根据 Boolean 值设置 View 可见性。
 *
 * @param visible 是否可见
 * @param goneIfFalse 不可见时是否 GONE（true 为 GONE，false 为 INVISIBLE）
 */
fun View.setVisible(visible: Boolean, goneIfFalse: Boolean = true) {
    visibility = when {
        visible -> View.VISIBLE
        goneIfFalse -> View.GONE
        else -> View.INVISIBLE
    }
}

/**
 * 批量设置 View 可见性。
 */
fun setVisible(vararg views: View, visible: Boolean, goneIfFalse: Boolean = true) {
    views.forEach { it.setVisible(visible, goneIfFalse) }
}

@Deprecated(
    message = "Use postDelay() to avoid shadowing View.postDelayed()",
    replaceWith = ReplaceWith("postDelay(delayMillis, action)")
)
fun View.postDelayed(delayMillis: Long, action: () -> Unit) {
    postDelay(delayMillis, action)
}

/**
 * 延迟执行操作（基于 View.postDelayed）。
 *
 * @param delayMillis 延迟时间（毫秒）
 * @param action 要执行的操作
 */
fun View.postDelay(delayMillis: Long, action: () -> Unit) {
    postDelayed(action, delayMillis)
}

/**
 * 切换 View 可见性（VISIBLE ↔ GONE）。
 *
 * @return 新的可见性
 */
fun View.toggleVisibility(): Int {
    val newVisibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    visibility = newVisibility
    return newVisibility
}
