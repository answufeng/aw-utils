package com.answufeng.utils

import android.view.View

fun View.onClick(interval: Long = 500L, onClick: (View) -> Unit) {
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

fun View.visible() { visibility = View.VISIBLE }

fun View.invisible() { visibility = View.INVISIBLE }

fun View.gone() { visibility = View.GONE }

fun View.setVisible(visible: Boolean, goneIfFalse: Boolean = true) {
    visibility = when {
        visible -> View.VISIBLE
        goneIfFalse -> View.GONE
        else -> View.INVISIBLE
    }
}

fun setVisible(vararg views: View, visible: Boolean, goneIfFalse: Boolean = true) {
    views.forEach { it.setVisible(visible, goneIfFalse) }
}

fun View.postDelayed(delayMillis: Long, action: () -> Unit) {
    postDelayed(action, delayMillis)
}

fun View.toggleVisibility(): Int {
    val newVisibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
    visibility = newVisibility
    return newVisibility
}
