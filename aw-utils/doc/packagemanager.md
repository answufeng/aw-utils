# PackageManagerExt — PackageManager 兼容封装（内部）

> 源码：[PackageManagerExt.kt](../src/main/java/com/answufeng/utils/PackageManagerExt.kt)

## 说明

此文件为**内部工具**，提供 `PackageManager.getPackageInfoCompat()` 方法，统一处理 API 33+ 的 `PackageInfoFlags` 参数变化。

```kotlin
// 内部使用，不对外暴露
packageManager.getPackageInfoCompat(packageName, PackageManager.GET_SIGNATURES)
```

> 库内所有需要 `getPackageInfo` 的地方均通过此方法调用，确保高低版本兼容。
