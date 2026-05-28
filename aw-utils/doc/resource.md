# ResourceExt — 资源读取兼容

> 源码：[ResourceExt.kt](../src/main/java/com/answufeng/utils/ResourceExt.kt)

```kotlin
context.getColorCompat(R.color.primary)
context.getDrawableCompat(R.drawable.ic_check)
```

> 内部使用 `ContextCompat`，兼容低版本 API。
