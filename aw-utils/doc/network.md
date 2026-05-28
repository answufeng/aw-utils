# NetworkExt — 网络连通性 / 类型 / Flow 监听

> 源码：[NetworkExt.kt](../src/main/java/com/answufeng/utils/NetworkExt.kt)

## 权限

需在 Manifest 声明 `ACCESS_NETWORK_STATE`。

## 网络状态

```kotlin
context.isNetworkAvailable()       // 是否联网（INTERNET + VALIDATED）
context.isWifiConnected()          // 是否 Wi-Fi
context.isMobileDataConnected()    // 是否移动数据
context.getNetworkType()           // NetworkType 枚举
context.isNetworkType(NetworkType.WIFI)  // 判断指定类型
```

## NetworkType 枚举

| 值 | 说明 |
|----|------|
| `WIFI` | Wi-Fi |
| `CELLULAR` | 移动数据 |
| `ETHERNET` | 有线以太网 |
| `NONE` | 无网络 |

## Flow 监听

```kotlin
@OptIn(AwExperimentalApi::class)
lifecycleScope.launch {
    observeNetworkState().collect { available ->
        updateUI(available)
    }
}
```

- 首次订阅时发射当前状态
- 使用 `distinctUntilChanged()` 去重
- 自动注册/注销 `NetworkCallback`

> ⚠️ 部分机型在 `onAvailable` 后短时间内 `VALIDATED` 尚未就绪，可能出现短暂 `false`，UI 层可配合防抖。
