# CollectionExt — 集合空判断 / 安全连接

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
