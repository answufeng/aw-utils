# CleanExt — 缓存目录清理

> 源码：[CleanExt.kt](../src/main/java/com/answufeng/utils/CleanExt.kt)

## 用法

```kotlin
context.clearInternalCacheChildren()    // 清空 cacheDir 子项
context.clearInternalFilesChildren()    // 清空 filesDir 子项
context.clearExternalCacheChildren()    // 清空 externalCacheDir 子项（不可用返回 false）

dir.clearDirectoryChildren()            // 通用：删除目录下所有子项（不删目录本身）
```

> 常用于「设置页清理缓存」场景。单个文件删失败不会中断，整体仍可能返回 `true`。
