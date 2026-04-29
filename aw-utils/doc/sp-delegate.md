# SpDelegate — SharedPreferences 属性委托（已弃用）

> 源码：[SpDelegate.kt](../src/main/java/com/answufeng/utils/SpDelegate.kt)

> ⚠️ **已弃用**：推荐使用 [aw-store](https://github.com/answufeng/aw-store) 的 `MmkvDelegate` / `AwStore`（基于 MMKV）。

## 定义

```kotlin
object AppPrefs : SpDelegate("app_prefs") {
    var token by string("token", "")
    var userId by int("user_id", 0)
    var isFirstLaunch by boolean("first_launch", true)
    var tags by stringSet("tags")
}
```

## 初始化

```kotlin
// Application.onCreate 中调用一次
AppPrefs.init(applicationContext)
```

## 读写

```kotlin
AppPrefs.token = "abc123"
val t = AppPrefs.token  // "abc123"
```

> 未调用 `init` 就访问属性会抛出 `IllegalStateException`。

## 批量编辑

```kotlin
AppPrefs.edit {
    putString("token", "new_token")
    putInt("user_id", 123)
    remove("temp_key")
}
```

## 支持类型

| 方法 | 类型 |
|------|------|
| `string()` | String |
| `int()` | Int |
| `long()` | Long |
| `float()` | Float |
| `boolean()` | Boolean |
| `stringSet()` | Set\<String\>（防御性拷贝） |

## 其他操作

```kotlin
AppPrefs.isInitialized  // 是否已初始化
AppPrefs.clear()        // 清空所有数据
AppPrefs.remove("key")  // 删除指定键
```
