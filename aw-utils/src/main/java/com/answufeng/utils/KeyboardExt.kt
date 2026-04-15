package com.answufeng.utils

import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver

/**
 * 判断软键盘是否可见。
 *
 * @param rootView 根视图，通常为 activity.window.decorView
 * @param threshold 键盘高度阈值（dp），默认 200dp
 */
fun isKeyboardVisible(rootView: View, threshold: Int = 200): Boolean {
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    val screenHeight = rootView.rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > threshold * rootView.resources.displayMetrics.density
}

/**
 * 监听键盘可见性变化。
 *
 * @param rootView 根视图
 * @param callback 键盘可见性回调
 * @return 取消监听的函数
 */
fun observeKeyboardVisibility(rootView: View, callback: (visible: Boolean) -> Unit): () -> Unit {
    var lastVisible = false
    val listener = ViewTreeObserver.OnGlobalLayoutListener {
        val visible = isKeyboardVisible(rootView)
        if (visible != lastVisible) {
            lastVisible = visible
            callback(visible)
        }
    }
    rootView.viewTreeObserver.addOnGlobalLayoutListener(listener)
    return {
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(listener)
    }
}
