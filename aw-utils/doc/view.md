# ViewExt — 防抖点击 / 触摸区域 / 可见性 / Padding / Margin

> 源码：[ViewExt.kt](../src/main/java/com/answufeng/utils/ViewExt.kt)

## 防抖点击

```kotlin
view.debounceClick { /* 点击回调 */ }
view.debounceClick(interval = 1000L) { /* 自定义间隔 */ }
```

> 默认 500ms 防抖间隔，使用 `SystemClock.elapsedRealtime()` 计时。

## 扩大触摸区域

```kotlin
view.expandTouchArea(extraDp = 32)
view.expandTouchArea(extraDp = 16, delegateParent = parentLayout)
```

> 基于 `TouchDelegate`，同一父 View 仅保留最后一次设置的 touchDelegate。

## 可见性

```kotlin
view.visible()                              // VISIBLE
view.invisible()                            // INVISIBLE
view.gone()                                 // GONE
view.setVisible(true)                       // VISIBLE / GONE
view.setVisible(false, goneIfFalse = false) // INVISIBLE
view.toggleVisibility()                     // VISIBLE ↔ GONE
setVisible(vararg views, visible = true)    // 批量设置
listOf(view1, view2).setVisible(true)       // List 版本
```

## 延迟执行

```kotlin
view.postDelay(300L) { /* 延迟 300ms 执行 */ }
```

## Padding / Margin DSL

```kotlin
view.updatePadding {
    start = 16.dpToPx(context)
    end = 16.dpToPx(context)
    top = 8.dpToPx(context)
}

view.updateMargin {
    start = 16.dpToPx(context)
    end = 16.dpToPx(context)
}
```

> `updatePadding` / `updateMargin` 内部使用 `setPaddingRelative` / `marginStart` / `marginEnd`，在 RTL 布局下自动适配方向。
> `left` / `right` 为 `start` / `end` 的别名，推荐使用 `start` / `end`。
> `updateMargin` 需要 LayoutParams 为 `MarginLayoutParams`，否则抛出 `IllegalStateException`。

## 尺寸设置

```kotlin
view.setWidth(200)                  // 像素
view.setHeight(100)
view.setWidthDp(16)                 // dp 自动转换
view.setHeightDp(16)
```

## 单方向 Padding

```kotlin
view.setPaddingStart(16)   // RTL 感知
view.setPaddingTop(8)
view.setPaddingEnd(16)     // RTL 感知（原 setPaddingRight）
view.setPaddingBottom(8)
```

> `setPaddingStart` / `setPaddingEnd` 使用 `setPaddingRelative`，在 RTL 布局下自动适配方向。

## 布局方向

```kotlin
view.isLayoutRtl()   // 是否 RTL 布局
```

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `View.onClick()` | `debounceClick()` |
| `View.postDelayed()` | `postDelay()`（避免遮蔽 View.postDelayed） |
