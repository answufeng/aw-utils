# AwLog — 轻量日志工具（已弃用）

> 源码：[AwLog.kt](../src/main/java/com/answufeng/utils/AwLog.kt)

> ⚠️ **已弃用**：推荐使用 [aw-log](https://github.com/answufeng/aw-log) 的 `AwLogger`（文件日志、脱敏、崩溃链）。

## 初始化

```kotlin
AwLog.init(isDebug = BuildConfig.DEBUG, prefix = "MyApp")
AwLog.init(isDebug = true, prefix = "MyApp", minLevel = AwLog.Level.WARN)
```

## 使用

```kotlin
AwLog.d("Network", "请求成功")       // Tag: MyApp-Network
AwLog.e("DB", "写入失败", exception)  // Tag: MyApp-DB

// Lambda 延迟拼接（关闭日志时不执行字符串拼接）
AwLog.d("Network") { "响应数据: ${response.body}" }
```

## 日志级别

| 级别 | 优先级 |
|------|--------|
| `VERBOSE` | 2 |
| `DEBUG` | 3 |
| `INFO` | 4 |
| `WARN` | 5 |
| `ERROR` | 6 |
| `NONE` | MAX（静默） |

> `AwLog` 继续作为零依赖的轻量日志工具维护，适用于简单场景。
