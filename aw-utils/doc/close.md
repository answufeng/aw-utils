# CloseExt — 静默关闭 Closeable

> 源码：[CloseExt.kt](../src/main/java/com/answufeng/utils/CloseExt.kt)

## closeQuietly

```kotlin
val input: InputStream? = null
val output: OutputStream? = null
try {
    // ...
} finally {
    input?.closeQuietly()
    output?.closeQuietly()
}
```

> 忽略关闭时抛出的异常，避免掩盖原始错误。

## closeAllQuietly

```kotlin
try {
    // ...
} finally {
    closeAllQuietly(input, output, reader, writer)
}
```

> 批量静默关闭，即使某个 close 抛出异常也会继续关闭剩余的 Closeable。
