package com.answufeng.utils

import androidx.fragment.app.Fragment

/**
 * 判断 Fragment 是否仍处于可安全操作 UI 的状态。
 *
 * 在异步回调中更新 UI 前应先检查此属性。
 */
val Fragment.isAlive: Boolean
    get() {
        if (!isAdded || isDetached || view == null) return false
        val act = activity ?: return false
        return act.isAlive
    }
