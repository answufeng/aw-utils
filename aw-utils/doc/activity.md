# ActivityExt — 泛型启动 / Extra / Arguments / 生命周期判断

> 源码：[ActivityExt.kt](../src/main/java/com/answufeng/utils/ActivityExt.kt)

## 泛型启动 Activity

```kotlin
// Context
startActivity<DetailActivity>()
startActivity<DetailActivity>(bundleOf("id" to 42))
startActivity<DetailActivity> {
    putExtra("id", 42)
    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
}

// Fragment
startActivity<DetailActivity>(bundleOf("id" to 42))
```

## 结果与 Extra

```kotlin
activity.finishWithResult(Activity.RESULT_OK, data)

val id: Int? = extraOrNull("id")
val name: String? = extraOrNull("name")
val idOrDefault: Int = extraOrDefault("id", -1)
```

> 基本类型（Int/Long/Boolean/Float/Double）使用 `containsKey` 判断，避免不存在时返回默认值 0/false 而非 null。

## Fragment Arguments

```kotlin
val count: Int? = argumentOrNull("count")
val countOrDefault: Int = argumentOrDefault("count", 0)
```

## 生命周期判断

```kotlin
activity.isDestroyedCompat  // API 17+ 兼容
activity.isAlive            // !isFinishing && !isDestroyedCompat
```

> 在异步回调中使用 Activity 前应先检查 `isAlive`，避免在销毁后执行 UI 操作导致崩溃。
