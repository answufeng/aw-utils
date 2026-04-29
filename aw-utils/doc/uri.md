# UriExt — Uri 转文件路径 / 文件名 / MIME 类型

> 源码：[UriExt.kt](../src/main/java/com/answufeng/utils/UriExt.kt)

## 文件路径

```kotlin
uri.toFilePath(context)  // String? — 转换失败返回 null
```

> ⚠️ 并非所有 content Uri 都能转换为文件路径（如 Google Drive 等云存储）。

## 文件名

```kotlin
uri.getFileName(context)  // String? — 优先查 DISPLAY_NAME，失败从 lastPathSegment 推断
```

## MIME 类型

```kotlin
uri.getMimeType(context)  // "image/png" — 无法确定时返回 "application/octet-stream"
```

## 本地判断

```kotlin
uri.isLocalFile()  // Boolean — 粗略判断是否为设备侧可访问内容
```

> `file` 方案视为本地；`content` 方案默认视为本地，但排除已知的云端 Provider（如 Google Docs/Photos）。
