# Annotations — 实验性 API 标记注解

> 源码：[Annotations.kt](../src/main/java/com/answufeng/utils/Annotations.kt)

## AwExperimentalApi

标记实验性 API，调用方需显式 Opt-In：

```kotlin
@OptIn(AwExperimentalApi::class)
val px = 100.dp
```

或文件级 Opt-In：

```kotlin
@file:OptIn(AwExperimentalApi::class)
```

### 当前标记为实验性的 API

| 模块 | API |
|------|-----|
| ContextExt | `Number.dp` / `dpF` / `sp` / `spF` |
| DateExt | `Long.toFriendlyTime()` |
| DeviceExt | `Context.deviceSummary()` |
| NetworkExt | `Context.observeNetworkState()` |

> 标记此注解的 API 可能在未来版本中变更或移除，不保证向后兼容。
