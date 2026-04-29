# VolumeExt — 系统音量读取与设置

> 源码：[VolumeExt.kt](../src/main/java/com/answufeng/utils/VolumeExt.kt)

## 权限

设置音量建议在 Manifest 声明 `MODIFY_AUDIO_SETTINGS`（非危险权限，安装时授予）。

## 读取音量

```kotlin
context.getStreamVolume()                    // STREAM_MUSIC 当前档位
context.getStreamVolume(AudioManager.STREAM_RING)  // 指定流类型
context.getStreamMaxVolume()                 // 最大档位
```

## 设置音量

```kotlin
context.setStreamVolume(index = 5, flags = AudioManager.FLAG_SHOW_UI)
context.setStreamVolume(
    streamType = AudioManager.STREAM_MUSIC,
    index = 10,
    flags = AudioManager.FLAG_SHOW_UI
)
```

> `index` 会自动 `coerceIn(0, maxVolume)`，不会越界。
