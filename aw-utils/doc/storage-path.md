# StoragePathExt — 应用存储路径 / 存储容量

> 源码：[StoragePathExt.kt](../src/main/java/com/answufeng/utils/StoragePathExt.kt)

## 路径属性

```kotlin
context.internalCacheDir       // cacheDir
context.internalFilesDir       // filesDir
context.externalAppCacheDir    // externalCacheDir（可能 null）
context.externalAppFilesDir()  // getExternalFilesDir(type)
context.externalAppFilesDir(Environment.DIRECTORY_PICTURES)
context.appObbDir              // obbDir（可能 null）
context.noBackupFilesDirPath   // noBackupFilesDir（API 21+）
```

> `externalAppCacheDir` / `externalAppFilesDir` / `appObbDir` 在无外置存储或介质不可用时可能为 `null`。

## 存储容量

```kotlin
context.internalStorageTotalSize()      // 内部存储总容量（字节）
context.internalStorageAvailableSize()  // 内部存储可用容量（字节）
context.externalStorageTotalSize()      // 外部存储总容量（字节），无外存返回 0
context.externalStorageAvailableSize()  // 外部存储可用容量（字节），无外存返回 0
```

> 基于 `StatFs` 实现，兼容 API 18+。

## 相关

- [AssetsRawExt](assets-raw.md) — Assets / Raw 资源读取
- [CleanExt](clean.md) — 缓存目录清理
