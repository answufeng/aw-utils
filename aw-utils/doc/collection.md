# CollectionExt — 集合空判断 / 安全连接 / 默认值

> 源码：[CollectionExt.kt](../src/main/java/com/answufeng/utils/CollectionExt.kt)

## 非空执行

```kotlin
list?.ifNotEmpty { items ->
    adapter.submitList(items.toList())
}
```

## 空时执行

```kotlin
list?.ifEmpty {
    showEmptyView()
}
```

## 安全 joinToString

```kotlin
val tags: List<String>? = null
tags.safeJoinToString()              // ""
tags.safeJoinToString("|")           // ""
listOf("a", "b").safeJoinToString()  // "a, b"
listOf("a", "b").safeJoinToString("|") { it.uppercase() }  // "A|B"
```

> null 或空集合返回空字符串，避免 NPE。

## 默认值

```kotlin
val items: List<String>? = null
items.orEmptyList { listOf("default") }  // ["default"]

val arr: Array<String>? = null
arr.orEmptyArray { arrayOf("fallback") } // ["fallback"]

emptyList<String>().orEmptyList { listOf("default") }  // ["default"]
listOf("a").orEmptyList { listOf("default") }          // ["a"]
```

## 数组空判断

```kotlin
val arr: Array<String>? = null
arr.isNullOrEmpty()  // true
```
