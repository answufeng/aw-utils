package com.answufeng.utils

/**
 * 集合非空且有元素时执行操作
 *
 * ```kotlin
 * list?.ifNotEmpty { items -> adapter.submitList(items.toList()) }
 * ```
 */
inline fun <T> Collection<T>?.ifNotEmpty(action: (Collection<T>) -> Unit) {
    if (!isNullOrEmpty()) action(this)
}

/**
 * 集合为空或 null 时执行操作
 *
 * ```kotlin
 * list?.ifEmpty { showEmptyView() }
 * ```
 */
inline fun <T> Collection<T>?.ifEmpty(action: () -> Unit) {
    if (isNullOrEmpty()) action()
}

/**
 * 安全的 joinToString，null 或空集合返回空字符串
 *
 * ```kotlin
 * val tags: List<String>? = null
 * tags.safeJoinToString()  // ""
 * ```
 */
fun <T> Collection<T>?.safeJoinToString(
    separator: CharSequence = ", ",
    transform: ((T) -> CharSequence)? = null
): String {
    if (isNullOrEmpty()) return ""
    return joinToString(separator, transform = transform)
}

/**
 * 集合为空或 null 时返回默认值。
 *
 * ```kotlin
 * val items: List<String>? = null
 * val result = items.orEmptyList { listOf("default") }
 * ```
 */
fun <T> Collection<T>?.orEmptyList(defaultValue: () -> List<T>): List<T> {
    return if (isNullOrEmpty()) defaultValue() else toList()
}

/**
 * 数组为空时返回默认值。
 *
 * ```kotlin
 * val arr: Array<String>? = null
 * val result = arr.orEmptyArray { arrayOf("default") }
 * ```
 */
fun <T> Array<T>?.orEmptyArray(defaultValue: () -> Array<T>): Array<T> {
    return if (isNullOrEmpty()) defaultValue() else this!!
}

/**
 * 判断数组是否为空或 null。
 */
fun <T> Array<T>?.isNullOrEmpty(): Boolean = this == null || isEmpty()
