# ImageViewExt — ImageView tint 着色

> 源码：[ImageViewExt.kt](../src/main/java/com/answufeng/utils/ImageViewExt.kt)

## 用法

```kotlin
imageView.setTint(Color.RED)                    // 设置 tint 颜色
imageView.setTintRes(R.color.primary)            // 设置 tint 颜色（资源 ID）
imageView.clearTint()                            // 清除 tint
imageView.setImageWithTint(R.drawable.icon, Color.WHITE)  // 设置图片 + tint
```

> 使用 `ImageViewCompat.setImageTintList()`，避免 Drawable 共享副作用。
