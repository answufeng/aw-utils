# ProcessExt — 主线程判断 / UI 线程执行

> 源码：[ProcessExt.kt](../src/main/java/com/answufeng/utils/ProcessExt.kt)

## 主线程判断

```kotlin
if (isMainThread) { /* ... */ }
```

## 在主线程执行

```kotlin
runOnUiThread {
    // 若已在主线程则直接执行，否则通过 Handler 投递
}

val task = runOnUiThreadDelayed(300L) {
    // 延迟 300ms 在主线程执行
}

removeUiThreadCallback(task)  // 取消执行
```

> 内部使用 `Handler(Looper.getMainLooper())`，适用于无 Activity/View 上下文的场景。
> 若在 Activity/View 中，优先使用 `Activity.runOnUiThread()` 或 `View.post()`。
