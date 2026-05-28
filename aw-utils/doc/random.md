# RandomExt — 随机字符串 / 列表采样

> 源码：[RandomExt.kt](../src/main/java/com/answufeng/utils/RandomExt.kt)

## 随机字符串

```kotlin
randomString(8)          // "aB3xK9pQ"（字母数字）
randomNumericString(6)   // "384729"（纯数字）
randomLetterString(10)   // "XkLmNpQrSt"（纯字母）
```

## 列表随机

```kotlin
listOf("A", "B", "C").randomElement()       // 随机选一个
listOf("A", "B", "C", "D").randomElements(2) // 随机选 2 个不重复元素
```

> `randomElements` 使用 Fisher-Yates 部分采样算法，避免对大列表进行完整 `shuffled()`。

整数、颜色等请使用 Kotlin 标准库：`(1..100).random()`、`Color.argb(...)`。
