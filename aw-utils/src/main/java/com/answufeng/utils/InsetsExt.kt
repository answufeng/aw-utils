package com.answufeng.utils

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * 监听 WindowInsets 变化。
 *
 * @param block 处理 insets 并返回 consumed insets
 * @return 移除监听的函数
 */
fun View.doOnApplyWindowInsets(block: (view: View, insets: WindowInsetsCompat) -> WindowInsetsCompat): () -> Unit {
    val listener =
        OnApplyWindowInsetsListener { v, insets ->
            block(v, insets)
        }
    ViewCompat.setOnApplyWindowInsetsListener(this, listener)
    return { ViewCompat.setOnApplyWindowInsetsListener(this, null) }
}

/**
 * 获取当前系统栏（状态栏 + 导航栏）Insets。
 */
fun View.getSystemBarsInsets(): Insets {
    val rootInsets = rootWindowInsets ?: return Insets.NONE
    return WindowInsetsCompat.toWindowInsetsCompat(rootInsets)
        .getInsets(WindowInsetsCompat.Type.systemBars())
}

/**
 * 获取当前 IME（软键盘）Insets。
 */
fun View.getImeInsets(): Insets {
    val rootInsets = rootWindowInsets ?: return Insets.NONE
    return WindowInsetsCompat.toWindowInsetsCompat(rootInsets)
        .getInsets(WindowInsetsCompat.Type.ime())
}

/**
 * 为 View 应用系统栏 padding（配合 [com.answufeng.utils.transparentSystemBars] 等沉浸式 API）。
 *
 * @param applyLeft 是否应用左侧 padding
 * @param applyTop 是否应用顶部 padding
 * @param applyRight 是否应用右侧 padding
 * @param applyBottom 是否应用底部 padding
 */
fun View.applySystemBarsPadding(
    applyLeft: Boolean = true,
    applyTop: Boolean = true,
    applyRight: Boolean = true,
    applyBottom: Boolean = true,
) {
    doOnApplyWindowInsets { v, insets ->
        val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(
            if (applyLeft) bars.left else v.paddingLeft,
            if (applyTop) bars.top else v.paddingTop,
            if (applyRight) bars.right else v.paddingRight,
            if (applyBottom) bars.bottom else v.paddingBottom,
        )
        insets
    }
    requestApplyInsets()
}
