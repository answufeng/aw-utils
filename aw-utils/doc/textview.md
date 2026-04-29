# TextViewExt — Compound Drawable / Drawable Tint

> 源码：[TextViewExt.kt](../src/main/java/com/answufeng/utils/TextViewExt.kt)

## 设置单侧 Drawable

```kotlin
textView.setDrawableStart(drawable)          // 起始侧（RTL 适配）
textView.setDrawableEnd(drawable)            // 结束侧
textView.setDrawableTop(drawable)            // 顶部
textView.setDrawableBottom(drawable)         // 底部
```

### 指定尺寸

```kotlin
textView.setDrawableStart(drawable, boundsWidth = 24, boundsHeight = 24)
```

### 资源 ID 版本

```kotlin
textView.setDrawableStartRes(R.drawable.icon)
textView.setDrawableEndRes(R.drawable.arrow)
textView.setDrawableTopRes(R.drawable.badge)
textView.setDrawableBottomRes(R.drawable.indicator)
```

## 清除 Drawable

```kotlin
textView.clearDrawables()
```

## Drawable Tint

```kotlin
textView.setDrawableTint(Color.RED)  // 为所有 compound drawable 设置 tint
```

> 使用 `DrawableCompat.wrap()` + `setTint()`，适配 RTL 方向。
