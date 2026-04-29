# AppExt — 应用安装 / Debug / 启动 / 图标 / 签名 / 版本信息

> 源码：[AppExt.kt](../src/main/java/com/answufeng/utils/AppExt.kt)

## 安装判断

```kotlin
context.isAppInstalled("com.tencent.mm")  // Boolean
```

> 内部使用 `getApplicationInfoCompat()` 兼容 API 33+ 的 `ApplicationInfoFlags` 变更。

## Debug 判断

```kotlin
context.isAppDebug()                      // 当前应用
context.isAppDebug("com.example.app")     // 指定包名
```

## 启动应用

```kotlin
context.launchApp("com.tencent.mm")  // Boolean — 未安装或无启动入口返回 false
```

## 应用信息

```kotlin
context.getAppIcon("com.tencent.mm")       // Drawable?
context.getAppName("com.tencent.mm")       // "微信"
context.isSystemApp(context.packageName)   // Boolean
context.getAppSignatureSHA1()              // 当前应用签名 SHA-1（40 位小写十六进制）
context.getAppSignatureSHA1("com.example") // 指定应用
context.openAppDetailSettings()            // 打开当前应用详情设置
context.openAppDetailSettings("com.example") // 指定应用
```

## 版本信息

```kotlin
context.getAppVersionName()              // "1.2.3"
context.getAppVersionCode()              // 123L
context.getAppMinSdkVersion()            // 21（API 24+）
context.getAppTargetSdkVersion()         // 34
context.getAppVersionName("com.example") // 指定包名
context.getAppVersionCode("com.example") // 指定包名
```

> `getAppVersionCode()` 在 API 28+ 返回 `longVersionCode`，低版本返回 `versionCode.toLong()`。

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `isAppForeground()` | `ProcessLifecycleOwner` 方案 |
| `Context.appVersionName()` (SystemExt) | `Context.getAppVersionName()` (AppExt) |
| `Context.appVersionCode()` (SystemExt) | `Context.getAppVersionCode()` (AppExt) |

> `isAppForeground()` 在 Android 10+ 因 `runningAppProcesses` 受限而不可靠。

## 相关

- [ActivityExt](activity.md) — Activity 启动与 Extra
- [MetaDataExt](metadata.md) — Manifest meta-data
