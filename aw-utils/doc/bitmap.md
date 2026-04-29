# BitmapExt — Bitmap 变换 / 压缩 / 采样解码

> 源码：[BitmapExt.kt](../src/main/java/com/answufeng/utils/BitmapExt.kt)

## Drawable → Bitmap

```kotlin
drawable.toBitmap()  // 若已是 BitmapDrawable 则直接获取，否则绘制
```

## 缩放

```kotlin
bitmap.scale(newWidth, newHeight)    // 指定宽高
bitmap.scaleMaxSize(1024)            // 最大边不超过 1024px
```

## 圆形 / 圆角

```kotlin
bitmap.toCircle()          // 裁剪为圆形
bitmap.toRounded(16f)      // 裁剪为圆角矩形（radius 单位：px）
```

> 使用 `BitmapShader` 实现，兼容硬件加速。调用方负责回收原始 Bitmap。

## 旋转

```kotlin
bitmap.rotate(90f)  // 顺时针旋转 90°
```

## 压缩保存

```kotlin
bitmap.compressTo(file, format = Bitmap.CompressFormat.PNG, quality = 80)
```

## 采样解码

```kotlin
val bitmap = File(cacheDir, "large.jpg").decodeBitmapSampled(1080, 1080)
```

> 先读取边界（`inJustDecodeBounds`），计算采样大小，再解码，降低 OOM 风险。

```kotlin
val sampleSize = calculateSampleSize(options, maxWidth, maxHeight)
```

> `calculateSampleSize` 也接受 `BitmapFactoryOptionsCompat` 包装类型。
