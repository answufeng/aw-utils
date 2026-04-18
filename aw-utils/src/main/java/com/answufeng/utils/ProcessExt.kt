package com.answufeng.utils

import android.os.Handler
import android.os.Looper

/**
 * 判断当前是否在主线程。
 */
val isMainThread: Boolean get() = Looper.myLooper() == Looper.getMainLooper()

private val mainHandler by lazy { Handler(Looper.getMainLooper()) }

/**
 * 在主线程执行操作。
 *
 * 若已在主线程则直接执行，否则通过 Handler 投递到主线程。
 */
fun runOnUiThread(action: () -> Unit) {
    if (isMainThread) {
        action()
    } else {
        mainHandler.post(action)
    }
}

/**
 * 延迟在主线程执行操作。
 *
 * @param delayMillis 延迟时间（毫秒）
 * @param action 要执行的操作
 * @return [Runnable] 可传入 [removeUiThreadCallback] 取消执行
 */
fun runOnUiThreadDelayed(delayMillis: Long, action: () -> Unit): Runnable {
    val runnable = Runnable { action() }
    mainHandler.postDelayed(runnable, delayMillis)
    return runnable
}

/**
 * 从主线程 Handler 中移除待执行的回调。
 *
 * ```kotlin
 * val task = runOnUiThreadDelayed(300L) { doSomething() }
 * removeUiThreadCallback(task) // 取消
 * ```
 */
fun removeUiThreadCallback(action: Runnable) {
    mainHandler.removeCallbacks(action)
}
