package com.answufeng.utils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * 在当前 View 上显示 Snackbar。
 */
fun View.showSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT,
): Snackbar {
    return Snackbar.make(this, message, duration).also { it.show() }
}

/**
 * 在当前 Activity 的 decorView 上显示 Snackbar。
 */
fun Activity.showSnackbar(
    message: CharSequence,
    duration: Int = Snackbar.LENGTH_SHORT,
): Snackbar {
    return window.decorView.showSnackbar(message, duration)
}
