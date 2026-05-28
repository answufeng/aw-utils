# Annotations — 实验性 API 标记注解

> 源码：[Annotations.kt](../src/main/java/com/answufeng/utils/Annotations.kt)

## AwExperimentalApi

标记实验性 API，调用方需显式 Opt-In：

```kotlin
@OptIn(AwExperimentalApi::class)
val friendly = timestamp.toFriendlyTime()
```

或文件级 Opt-In：

```kotlin
@file:OptIn(AwExperimentalApi::class)
```

### 当前标记为实验性的 API

| 模块 | API |
|------|-----|
| DateExt | `Long.toFriendlyTime()` |
| DeviceExt | `Context.deviceSummary()` |
| NetworkExt | `Context.observeNetworkState()` |

> 标记此注解的 API 可能在未来版本中变更，使用前请评估是否接受行为调整。
