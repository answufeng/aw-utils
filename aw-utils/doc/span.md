# SpanExt — 富文本 Span DSL

> 源码：[SpanExt.kt](../src/main/java/com/answufeng/utils/SpanExt.kt)

## 单一样式

```kotlin
"Hello".spanColor(Color.RED)           // 前景色
"Hello".spanBackgroundColor(Color.YELLOW)  // 背景色
"Hello".spanBold()                     // 加粗
"Hello".spanItalic()                   // 斜体
"Hello".spanBoldItalic()               // 加粗斜体
"Hello".spanUnderline()                // 下划线
"Hello".spanStrikethrough()            // 删除线
"Hello".spanSize(24)                   // 字体大小（px）
"Hello".spanSizeDp(16, context)        // 字体大小（dp）
"Hello".spanSuperscript()              // 上标
"Hello".spanSubscript()                // 下标
```

> 所有样式方法均支持 `start` / `end` 参数指定作用范围，默认为整个字符串。

## 可点击 Span

```kotlin
"查看协议".spanClickable(start = 0, end = 4, onClick = { /* ... */ })
```

> 需设置 `textView.movementMethod = LinkMovementMethod.getInstance()`，或使用 `enableClickableSpan()`。

## 图片 Span

```kotlin
"  分类".spanImage(drawable, start = 0, end = 1)
```

## DSL 组合

```kotlin
textView.text = spannable {
    append("价格：".spanBold())
    append("¥99".spanColor(Color.RED))
    append(" ¥199".spanStrikethrough())
}
```

## 辅助

```kotlin
textView.enableClickableSpan()  // 设置 LinkMovementMethod + 透明高亮
```
