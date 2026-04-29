# FileExt — 文件大小 / 哈希 / 复制移动 / 安全删除 / 重命名 / 行数 / 目录列表

> 源码：[FileExt.kt](../src/main/java/com/answufeng/utils/FileExt.kt)

## 文件大小

```kotlin
file.friendlySize()       // "1.5 KB"
1536L.toFriendlySize()    // "1.5 KB"
dir.totalSize()           // 递归计算目录总大小（含符号链接环检测）
```

## 文件类型判断

```kotlin
file.extensionName   // "jpg"（小写扩展名）
file.isImage()       // jpg/jpeg/png/gif/bmp/webp/svg/ico/tiff/tif
file.isVideo()       // mp4/3gp/avi/flv/mkv/mov/wmv/webm/m4v/ts
file.isAudio()       // mp3/wav/ogg/aac/flac/mid/midi/amr/m4a/wma
```

## 安全删除

```kotlin
file.safeDeleteRecursively()  // 递归删除，符号链接目录仅删链接本身
```

## 目录操作

```kotlin
file.ensureParentDir()           // 确保父目录存在
dir.clearDirectoryChildren()     // 删除目录下所有子项（不删目录本身）
```

## 哈希摘要

```kotlin
file.md5()       // 32 位小写十六进制
file.sha256()    // 64 位小写十六进制
```

## 文件读写

```kotlin
inputStream.writeToFile(file)     // InputStream → File（自动创建父目录）
file.readTextOrNull()             // 读取文本，失败返回 null
```

## 复制与移动

```kotlin
file.copyToFile(target)           // 返回 Boolean
file.copyToFileCatching(target)   // 返回 Result<Unit>（携带异常）

file.moveToFile(target)           // 先 renameTo，失败则 copy+delete
file.moveToFileCatching(target)   // Result 版本
```

## 重命名

```kotlin
val newFile = file.rename("new_name.txt")  // 成功返回新 File，失败返回 null
```

## 行数统计

```kotlin
file.lineCount()  // 文本文件行数，空文件返回 0，读取失败返回 -1
```

## 目录列表

```kotlin
dir.listDirFiles()                                    // 仅直接子项
dir.listDirFiles(recursive = true)                    // 递归所有子项
dir.listDirFiles { it.extension == "log" }            // 过滤 .log 文件
dir.listDirFiles(recursive = true) { it.isFile }      // 递归仅文件
```

## 相关

- [ZipExt](zip.md) — 压缩与解压
- [CleanExt](clean.md) — 缓存目录清理
- [EncodeExt](encode.md) — 编解码
