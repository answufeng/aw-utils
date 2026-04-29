# AssetsRawExt — Assets / Raw 资源读取

> 源码：[AssetsRawExt.kt](../src/main/java/com/answufeng/utils/AssetsRawExt.kt)

## 读取文本

```kotlin
context.readAssetText("config.json")           // 全部文本
context.readAssetLines("data.txt")             // 按行读取
context.readRawText(R.raw.sample)              // res/raw 全部文本
```

## 拷贝到文件

```kotlin
context.copyAssetToFile("a.txt", File(filesDir, "b.txt"))
```

- 自动创建目标父目录
- 返回 `Boolean`：成功为 `true`

## 相关

- [StoragePathExt](storage-path.md) — 存储路径
