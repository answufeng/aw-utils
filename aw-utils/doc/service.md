# ServiceExt — 服务启动 / 前台服务 / 停止服务

> 源码：[ServiceExt.kt](../src/main/java/com/answufeng/utils/ServiceExt.kt)

## 启动服务

```kotlin
context.startServiceCompat<MyService>()
context.startServiceCompat<MyService> {
    putExtra("key", "value")
}
```

## 前台服务

```kotlin
context.startForegroundServiceCompat<MyFgService>()
context.startForegroundServiceCompat<MyFgService> {
    putExtra("key", "value")
}
```

> Android 8.0+ 自动走 `startForegroundService()`，低版本回退为 `startService()`。

## 停止服务

```kotlin
context.stopServiceCompat<MyService>()
```
