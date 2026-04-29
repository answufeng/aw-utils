package com.answufeng.utils

import android.content.Context
import android.graphics.Rect
import android.os.SystemClock
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
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

/**
 * 向四边扩大可点击/可触摸区域（基于 [TouchDelegate]）。
 *
 * **注意**：同一父 View 上仅会保留**最后一次**设置的 [View.touchDelegate]；有多个子 View 需扩大时请在父层自定义
 * [TouchDelegate] 组合或使用更大的透明覆盖 View。
 *
 * @param extraDp 每边扩展的 dp（会转为像素）
 * @param delegateParent 承载 [TouchDelegate] 的父 [ViewGroup]；为 `null` 时使用 [View.getParent]
 */
fun View.expandTouchArea(
    extraDp: Int,
    context: Context = this.context,
    delegateParent: ViewGroup? = null,
) {
    require(extraDp >= 0) { "extraDp must be >= 0, got $extraDp" }
    val p = delegateParent ?: (parent as? ViewGroup) ?: return
    val extraPx = extraDp.dpToPx(context)
    p.post {
        val r = Rect()
        getHitRect(r)
        r.inset(-extraPx, -extraPx)
        p.touchDelegate = TouchDelegate(r, this)
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
 * DSL 风格更新 padding（RTL 感知）。
 *
 * ```kotlin
 * view.updatePadding {
 *     start = 16.dpToPx(context)
 *     end = 16.dpToPx(context)
 *     top = 8.dpToPx(context)
 * }
 * ```
 *
 * 内部使用 `setPaddingRelative`，在 RTL 布局下自动适配方向。
 * `left` / `right` 为 `start` / `end` 的别名，推荐使用 `start` / `end`。
 */
inline fun View.updatePadding(block: android.graphics.Rect.() -> Unit) {
    val rect = android.graphics.Rect(paddingStart, paddingTop, paddingEnd, paddingBottom)
    rect.block()
    setPaddingRelative(rect.left, rect.top, rect.right, rect.bottom)
}

/**
 * DSL 风格更新 margin（RTL 感知）。
 *
 * 需要 View 的 LayoutParams 为 [MarginLayoutParams]（如 LinearLayout.LayoutParams、FrameLayout.LayoutParams 等），
 * 否则抛出 [IllegalStateException]。
 *
 * ```kotlin
 * view.updateMargin {
 *     start = 16.dpToPx(context)
 *     end = 16.dpToPx(context)
 * }
 * ```
 *
 * 内部使用 `marginStart` / `marginEnd`，在 RTL 布局下自动适配方向。
 * `left` / `right` 为 `start` / `end` 的别名，推荐使用 `start` / `end`。
 */
inline fun View.updateMargin(block: android.graphics.Rect.() -> Unit) {
    val lp = layoutParams
    check(lp is MarginLayoutParams) { "View layoutParams must be MarginLayoutParams" }
    val rect = android.graphics.Rect(lp.marginStart, lp.topMargin, lp.marginEnd, lp.bottomMargin)
    rect.block()
    lp.marginStart = rect.left
    lp.topMargin = rect.top
    lp.marginEnd = rect.right
    lp.bottomMargin = rect.bottom
    layoutParams = lp
}

/**
 * 设置 View 宽度（像素）。
 *
 * @throws IllegalStateException 如果 View 尚未添加到父布局（layoutParams 为 null）
 */
fun View.setWidth(widthPx: Int) {
    val lp = layoutParams ?: throw IllegalStateException("View layoutParams is null — view may not be attached to a parent")
    lp.width = widthPx
    layoutParams = lp
}

/**
 * 设置 View 高度（像素）。
 *
 * @throws IllegalStateException 如果 View 尚未添加到父布局（layoutParams 为 null）
 */
fun View.setHeight(heightPx: Int) {
    val lp = layoutParams ?: throw IllegalStateException("View layoutParams is null — view may not be attached to a parent")
    lp.height = heightPx
    layoutParams = lp
}

/**
 * 设置 View 宽度（dp，自动转换为像素）。
 */
fun View.setWidthDp(widthDp: Int, context: android.content.Context = this.context) {
    setWidth(widthDp.dpToPx(context))
}

/**
 * 设置 View 高度（dp，自动转换为像素）。
 */
fun View.setHeightDp(heightDp: Int, context: android.content.Context = this.context) {
    setHeight(heightDp.dpToPx(context))
}

/**
 * 批量设置 View 可见性（List 版本）。
 */
fun List<View>.setVisible(visible: Boolean, goneIfFalse: Boolean = true) {
    forEach { it.setVisible(visible, goneIfFalse) }
}

/** 仅设置 paddingStart，其余方向保持不变（RTL 感知）。
 *
 * @param paddingStart 起始边 padding（像素，RTL 下为右侧）
 */
fun View.setPaddingStart(paddingStart: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/** 仅设置 paddingTop，其余方向保持不变。
 *
 * @param paddingTop 顶部 Padding 值（像素）
 */
fun View.setPaddingTop(paddingTop: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/** 仅设置 paddingEnd，其余方向保持不变（RTL 感知）。
 *
 * @param paddingEnd 结束边 padding（像素，RTL 下为左侧）
 */
fun View.setPaddingEnd(paddingEnd: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/** 仅设置 paddingBottom，其余方向保持不变。
 *
 * @param paddingBottom 底部 Padding 值（像素）
 */
fun View.setPaddingBottom(paddingBottom: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/**
 * 判断 View 的布局方向是否为 RTL（从右到左）。
 */
fun View.isLayoutRtl(): Boolean {
    return layoutDirection == View.LAYOUT_DIRECTION_RTL
}
