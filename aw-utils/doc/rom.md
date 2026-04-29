# RomExt — 国内/国际厂商 ROM 启发式判断

> 源码：[RomExt.kt](../src/main/java/com/answufeng/utils/RomExt.kt)

## 用法

```kotlin
Rom.isXiaomi()    // 含 Redmi、POCO
Rom.isHuawei()
Rom.isHonor()
Rom.isOppo()
Rom.isVivo()
Rom.isOnePlus()
Rom.isRealme()
Rom.isMeizu()
Rom.isSamsung()
Rom.isLenovo()
Rom.isGoogle()
```

## 说明

- 基于 `Build.MANUFACTURER` 与 `Build.BRAND` 的**启发式**匹配
- 非官方 ROM API，仅作分支参考
- `isXiaomi()` 额外覆盖 Redmi / POCO 子品牌
- 号段信息可能随厂商变化，建议定期更新

## 相关

- [DeviceExt](device.md) — 设备基本信息
