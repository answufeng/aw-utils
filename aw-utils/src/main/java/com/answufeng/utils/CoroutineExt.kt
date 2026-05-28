package com.answufeng.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withTimeoutOrNull

/**
 * 以指数退避策略重试 suspend 块。
 *
 * @param maxRetries 最大重试次数（不含首次执行）
 * @param initialBackoffMs 首次退避毫秒数
 * @param maxBackoffMs 退避上限毫秒数
 * @param factor 退避倍数
 * @param block 执行体；参数为当前尝试次数（从 0 开始）
 * @throws Exception 全部尝试失败时抛出最后一次异常
 */
suspend fun <T> retryWithBackoff(
    maxRetries: Int,
    initialBackoffMs: Long = 1_000L,
    maxBackoffMs: Long = 30_000L,
    factor: Double = 2.0,
    block: suspend (attempt: Int) -> T,
): T {
    var lastError: Exception? = null
    for (attempt in 0..maxRetries) {
        try {
            return block(attempt)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            lastError = e
            if (attempt < maxRetries) {
                val backoff =
                    (initialBackoffMs.toDouble() * Math.pow(factor, attempt.toDouble()))
                        .toLong()
                        .coerceAtMost(maxBackoffMs)
                delay(backoff)
            }
        }
    }
    throw lastError!!
}

/**
 * 以固定间隔重试 suspend 块。
 *
 * @param maxRetries 最大重试次数（不含首次执行）
 * @param intervalMs 重试间隔毫秒数
 * @param block 执行体；参数为当前尝试次数（从 0 开始）
 * @throws Exception 全部尝试失败时抛出最后一次异常
 */
suspend fun <T> retryWithFixedInterval(
    maxRetries: Int,
    intervalMs: Long = 1_000L,
    block: suspend (attempt: Int) -> T,
): T {
    var lastError: Exception? = null
    for (attempt in 0..maxRetries) {
        try {
            return block(attempt)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            lastError = e
            if (attempt < maxRetries) {
                delay(intervalMs)
            }
        }
    }
    throw lastError!!
}

/**
 * 在超时时间内等待 Flow 的首个元素。
 *
 * @param timeoutMs 超时毫秒数
 * @return 首个元素，超时或无元素时返回 null
 */
suspend fun <T> Flow<T>.firstOrNullWithTimeout(timeoutMs: Long): T? {
    return withTimeoutOrNull(timeoutMs) { firstOrNull() }
}

/**
 * 启动 async 并将异常捕获为 [Result]（不向外抛出）。
 */
fun <T> CoroutineScope.asyncSafe(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T,
): Deferred<Result<T>> {
    return async(Dispatchers.Default, start) {
        runCatching { block() }
    }
}
