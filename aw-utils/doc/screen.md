# ScreenExt — 屏幕方向 / 亮度 / 常亮 / 平板判断 / 系统亮度

> 源码：[ScreenExt.kt](../src/main/java/com/answufeng/utils/ScreenExt.kt)

## 屏幕方向

```kotlin
context.isTablet     // 是否平板
context.isLandscape  // 是否横屏
context.isPortrait   // 是否竖屏
```

## 窗口亮度

```kotlin
activity.getWindowBrightness()           // 0-255
activity.setWindowBrightness(200)        // 设置亮度
activity.resetWindowBrightness()         // 跟随系统
```

## 系统亮度

```kotlin
context.isAutoBrightnessEnabled()        // 是否开启自动亮度
context.setAutoBrightnessEnabled(true)   // 开启自动亮度（需 WRITE_SETTINGS 权限）
context.setAutoBrightnessEnabled(false)  // 关闭自动亮度
context.getSystemBrightness()            // 系统亮度值 0-255
```

> `setAutoBrightnessEnabled` 需要 `WRITE_SETTINGS` 权限或通过 `Settings.ACTION_MANAGE_WRITE_SETTINGS` 获取授权。

## 屏幕常亮

```kotlin
activity.isScreenKeepOn()        // 是否常亮
activity.setScreenKeepOn(true)   // 设置常亮
activity.setScreenKeepOn(false)  // 取消常亮
```

## 相关

- [ContextExt](context.md) — 屏幕尺寸 / 状态栏高度
- [BarExt](bar.md) — 状态栏 / 导航栏样式
