# FragmentExt — Fragment 生命周期工具

> 源码：[FragmentExt.kt](../src/main/java/com/answufeng/utils/FragmentExt.kt)

```kotlin
if (fragment.isAlive) {
    fragment.view?.text = "…"
}
```

> 参数读取请使用 [ActivityExt](activity.md) 中的 `argumentOrNull` / `argumentOrDefault`。
