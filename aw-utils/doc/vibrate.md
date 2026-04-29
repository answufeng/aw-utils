# VibrateExt — 振动

> 源码：[VibrateExt.kt](../src/main/java/com/answufeng/utils/VibrateExt.kt)

## 权限

需在 Manifest 声明 `android.permission.VIBRATE`。

## 用法

```kotlin
context.vibrate()                           // 短振动（约 50ms）
context.vibrate(200L)                       // 自定义时长
context.vibrate(longArrayOf(0, 100, 100, 100), repeat = -1)  // 模式振动
context.cancelVibrate()                     // 取消振动
```

> Android 12+ 使用 `VibratorManager.getDefaultVibrator()`，低版本回退 `Vibrator`。
