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

suspend fun <T> retryWithBackoff(
    maxRetries: Int,
    initialBackoffMs: Long = 1_000L,
    maxBackoffMs: Long = 30_000L,
    factor: Double = 2.0,
    block: suspend (attempt: Int) -> T
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
                val backoff = (initialBackoffMs.toDouble() * Math.pow(factor, attempt.toDouble()))
                    .toLong()
                    .coerceAtMost(maxBackoffMs)
                delay(backoff)
            }
        }
    }
    throw lastError!!
}

suspend fun <T> retryWithFixedInterval(
    maxRetries: Int,
    intervalMs: Long = 1_000L,
    block: suspend (attempt: Int) -> T
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

suspend fun <T> Flow<T>.firstOrNullWithTimeout(timeoutMs: Long): T? {
    return withTimeoutOrNull(timeoutMs) { firstOrNull() }
}

fun <T> CoroutineScope.asyncSafe(
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<Result<T>> {
    return async(Dispatchers.Default, start) {
        runCatching { block() }
    }
}
