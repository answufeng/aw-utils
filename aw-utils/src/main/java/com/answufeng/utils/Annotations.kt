package com.answufeng.utils

/**
 * 标记实验性 API，调用方需显式 Opt-In。
 *
 * 标记此注解的 API 可能在未来版本中变更或移除，不保证向后兼容。
 *
 * ```kotlin
 * @OptIn(AwExperimentalApi::class)
 * val px = 100.dp
 * ```
 */
@RequiresOptIn(
    message = "This API is experimental and may change in future versions.",
    level = RequiresOptIn.Level.WARNING
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY)
annotation class AwExperimentalApi
