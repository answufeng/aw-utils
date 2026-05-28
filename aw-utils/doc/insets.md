# InsetsExt — WindowInsets 读取与 padding

> 源码：[InsetsExt.kt](../src/main/java/com/answufeng/utils/InsetsExt.kt)

配合 [BarExt](bar.md) 的沉浸式 API 使用。

```kotlin
root.applySystemBarsPadding()

root.doOnApplyWindowInsets { view, insets ->
    val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
    // 自定义处理
    insets
}

val bars = root.getSystemBarsInsets()
val ime = root.getImeInsets()
```

> `applySystemBarsPadding` 会注册 Insets 监听并调用 `requestApplyInsets()`。
