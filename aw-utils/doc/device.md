# DeviceExt — 设备品牌 / 型号 / 系统版本

> 源码：[DeviceExt.kt](../src/main/java/com/answufeng/utils/DeviceExt.kt)

## Context 扩展属性

```kotlin
context.deviceBrand         // "Xiaomi"
context.deviceModel         // "Mi 14"
context.deviceManufacturer  // "Xiaomi"
context.osVersion           // "14"
context.sdkVersion          // 34
context.androidId           // "a1b2c3d4e5f6g7h8"
```

## 设备摘要

```kotlin
@OptIn(AwExperimentalApi::class)
context.deviceSummary()
// "Xiaomi Mi 14 | Android 14 (SDK 34)"
```

> 适合日志和错误上报场景。

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| 顶层 `deviceBrand` | `Context.deviceBrand` |
| 顶层 `deviceModel` | `Context.deviceModel` |
| 顶层 `deviceManufacturer` | `Context.deviceManufacturer` |
| 顶层 `osVersion` | `Context.osVersion` |
| 顶层 `sdkVersion` | `Context.sdkVersion` |
| 顶层 `deviceSummary()` | `Context.deviceSummary()` |

> 顶层属性已弃用以避免命名空间污染，统一迁移为 `Context` 扩展。

## 相关

- [RomExt](rom.md) — 厂商 ROM 判断
- [ScreenExt](screen.md) — 屏幕信息
