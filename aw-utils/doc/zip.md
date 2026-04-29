# ZipExt — 目录压缩 / 解压（含 Zip Slip 校验）

> 源码：[ZipExt.kt](../src/main/java/com/answufeng/utils/ZipExt.kt)

## 压缩

```kotlin
sourceDir.zipDirectoryTo(File(cacheDir, "out.zip"))
```

- 仅打包文件，跳过空目录条目
- 使用 `walkTopDown()`，默认不跟随符号链接
- 返回 `Boolean`：成功为 `true`

## 解压

```kotlin
zipFile.unzipToDirectory(targetDir)
```

- **Zip Slip 校验**：拒绝写到目标目录之外的路径
- 跳过 `../`、绝对路径等可疑条目
- 自动创建子目录
- 返回 `Boolean`：成功为 `true`

## 安全说明

解压时对每个条目路径做规范化（`canonicalFile`）校验，防止 [Zip Slip](https://security.snyk.io/research/zip-slip-vulnerability) 目录穿越攻击。
