package com.answufeng.utils

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup.MarginLayoutParams

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
        val now = SystemClock.elapsedRealtime()
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

/**
 * DSL 风格更新 padding。
 *
 * ```kotlin
 * view.updatePadding {
 *     left = 16.dpToPx(context)
 *     right = 16.dpToPx(context)
 * }
 * ```
 */
inline fun View.updatePadding(block: android.graphics.Rect.() -> Unit) {
    val rect = android.graphics.Rect(paddingLeft, paddingTop, paddingRight, paddingBottom)
    rect.block()
    setPadding(rect.left, rect.top, rect.right, rect.bottom)
}

/**
 * DSL 风格更新 margin。
 *
 * 需要 View 的 LayoutParams 为 [MarginLayoutParams]（如 LinearLayout.LayoutParams、FrameLayout.LayoutParams 等），
 * 否则抛出 [IllegalStateException]。
 *
 * ```kotlin
 * view.updateMargin {
 *     left = 16.dpToPx(context)
 * }
 * ```
 */
inline fun View.updateMargin(block: android.graphics.Rect.() -> Unit) {
    val lp = layoutParams
    check(lp is MarginLayoutParams) { "View layoutParams must be MarginLayoutParams" }
    val rect = android.graphics.Rect(lp.leftMargin, lp.topMargin, lp.rightMargin, lp.bottomMargin)
    rect.block()
    lp.leftMargin = rect.left
    lp.topMargin = rect.top
    lp.rightMargin = rect.right
    lp.bottomMargin = rect.bottom
    layoutParams = lp
}

/**
 * 设置 View 宽度（像素）。
 */
fun View.setWidth(widthPx: Int) {
    val lp = layoutParams ?: return
    lp.width = widthPx
    layoutParams = lp
}

/**
 * 设置 View 高度（像素）。
 */
fun View.setHeight(heightPx: Int) {
    val lp = layoutParams ?: return
    lp.height = heightPx
    layoutParams = lp
}

/**
 * 批量设置 View 可见性（List 版本）。
 */
fun List<View>.setVisible(visible: Boolean, goneIfFalse: Boolean = true) {
    forEach { it.setVisible(visible, goneIfFalse) }
}
