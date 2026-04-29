# FlashlightExt — 手电筒开关

> 源码：[FlashlightExt.kt](../src/main/java/com/answufeng/utils/FlashlightExt.kt)

## 用法

```kotlin
context.isTorchSupported()        // 是否有闪光灯
context.setTorchEnabled(true)     // 打开手电筒
context.setTorchEnabled(false)    // 关闭手电筒
```

> 返回 `Boolean`：是否未抛异常地完成调用（不表示硬件状态已稳定）。

## 权限

标准 AOSP 上通常无需权限；部分厂商机型需声明 `android.permission.CAMERA` 或需在系统设置中授权相机。

## 原理

使用 `CameraManager.setTorchMode()`，自动查找带闪光灯的摄像头 ID。
