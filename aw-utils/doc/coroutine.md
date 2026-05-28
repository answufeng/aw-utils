# CoroutineExt — 协程重试 / 超时 / 安全 async

> 源码：[CoroutineExt.kt](../src/main/java/com/answufeng/utils/CoroutineExt.kt)

## 重试

```kotlin
// 指数退避（默认 1s 起，×2，上限 30s）
val data = retryWithBackoff(maxRetries = 3) { attempt ->
    api.fetch()
}

// 固定间隔
val data = retryWithFixedInterval(maxRetries = 3, intervalMs = 500L) { attempt ->
    api.fetch()
}
```

> 不会捕获 [kotlinx.coroutines.CancellationException]，协程取消会正常向上抛出。

## Flow 超时

```kotlin
val first = flow.firstOrNullWithTimeout(timeoutMs = 3_000L)
```

## 安全 async

```kotlin
val deferred = scope.asyncSafe {
    heavyWork()
}
val result: Result<T> = deferred.await()
```

> 异常被包装为 `Result.failure`，不会导致 Coroutine 崩溃。

## 依赖

本模块通过 `api` 传递 `kotlinx-coroutines-core`，宿主可直接使用 `Flow`、`launch` 等 API。
