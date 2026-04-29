package com.answufeng.utils

import java.io.Closeable

/**
 * 静默关闭 [Closeable]，忽略任何异常。
 *
 * 适用于 finally 块中关闭流、数据库连接等场景，
 * 避免异常掩盖原始错误。
 */
fun Closeable.closeQuietly() {
    try {
        close()
    } catch (_: Exception) {
    }
}

/**
 * 批量静默关闭多个 [Closeable]。
 *
 * 即使某个 close 抛出异常，也会继续关闭剩余的 Closeable。
 *
 * ```kotlin
 * closeAllQuietly(input, output, reader)
 * ```
 */
fun closeAllQuietly(vararg closeables: Closeable?) {
    closeables.forEach { it?.closeQuietly() }
}
