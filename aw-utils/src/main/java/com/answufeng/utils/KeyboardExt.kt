package com.answufeng.utils

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver

/**
 * 判断软键盘是否可见。
 *
 * @param threshold 键盘高度阈值（dp），默认 200dp
 */
fun View.isKeyboardVisible(threshold: Int = 200): Boolean {
    val rect = Rect()
    getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > threshold * resources.displayMetrics.density
}

/**
 * 监听键盘可见性变化。
 *
 * @param callback 键盘可见性回调
 * @return 取消监听的函数
 */
fun View.observeKeyboardVisibility(callback: (visible: Boolean) -> Unit): () -> Unit {
    var lastVisible = false
    val listener = ViewTreeObserver.OnGlobalLayoutListener {
        val visible = isKeyboardVisible()
        if (visible != lastVisible) {
            lastVisible = visible
            callback(visible)
        }
    }
    viewTreeObserver.addOnGlobalLayoutListener(listener)
    return {
        viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }
}

/**
 * 判断当前 Activity 的软键盘是否可见。
 *
 * @param threshold 键盘高度阈值（dp），默认 200dp
 */
fun Activity.isKeyboardVisible(threshold: Int = 200): Boolean {
    return window.decorView.isKeyboardVisible(threshold)
}

/**
 * 监听当前 Activity 的键盘可见性变化。
 *
 * @param callback 键盘可见性回调
 * @return 取消监听的函数
 */
fun Activity.observeKeyboardVisibility(callback: (visible: Boolean) -> Unit): () -> Unit {
    return window.decorView.observeKeyboardVisibility(callback)
}
