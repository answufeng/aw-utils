# RandomExt — 随机字符串 / 随机数 / 随机颜色 / 随机采样

> 源码：[RandomExt.kt](../src/main/java/com/answufeng/utils/RandomExt.kt)

## 随机字符串

```kotlin
randomString(8)          // "aB3xK9pQ"（字母数字）
randomNumericString(6)   // "384729"（纯数字）
randomLetterString(10)   // "XkLmNpQrSt"（纯字母）
```

## 随机数

```kotlin
randomInt(1, 100)   // 1-100 的随机整数
randomLong(0, 9999) // 0-9999 的随机长整数
```

## 随机颜色

```kotlin
randomColor()          // 不透明随机颜色
randomColor(alpha = 128)  // 半透明随机颜色
```

## 列表随机

```kotlin
listOf("A", "B", "C").randomElement()       // 随机选一个
listOf("A", "B", "C", "D").randomElements(2) // 随机选 2 个不重复元素
```

> `randomElements` 使用 Fisher-Yates 部分采样算法，避免对大列表进行完整 `shuffled()`。
