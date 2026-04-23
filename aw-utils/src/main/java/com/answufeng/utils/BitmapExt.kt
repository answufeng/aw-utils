package com.answufeng.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.BitmapShader
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.File
import java.io.FileOutputStream

/**
 * 将 Drawable 转换为 Bitmap。
 *
 * 若 Drawable 已是 BitmapDrawable 则直接获取，
 * 否则根据 Drawable 的固有宽高绘制。若固有宽高 <= 0，使用 1x1 像素。
 */
fun Drawable.toBitmap(): Bitmap {
    if (this is BitmapDrawable) return bitmap
    val width = if (intrinsicWidth > 0) intrinsicWidth else 1
    val height = if (intrinsicHeight > 0) intrinsicHeight else 1
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    setBounds(0, 0, width, height)
    draw(canvas)
    return bitmap
}

/**
 * 将 Bitmap 缩放到指定宽高。
 */
fun Bitmap.scale(newWidth: Int, newHeight: Int): Bitmap {
    val matrix = Matrix()
    matrix.postScale(
        newWidth.toFloat() / width,
        newHeight.toFloat() / height
    )
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

/**
 * 将 Bitmap 按比例缩放，最大边不超过 maxSize。
 */
fun Bitmap.scaleMaxSize(maxSize: Int): Bitmap {
    if (width <= maxSize && height <= maxSize) return this
    val ratio = minOf(maxSize.toFloat() / width, maxSize.toFloat() / height)
    return scale((width * ratio).toInt(), (height * ratio).toInt())
}

/**
 * 将 Bitmap 裁剪为圆形。
 *
 * 使用 BitmapShader 实现，兼容硬件加速。
 * 调用方负责在不再需要时回收原始 Bitmap。
 */
fun Bitmap.toCircle(): Bitmap {
    val size = minOf(width, height)
    val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = BitmapShader(this@toCircle, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }
    val x = (width - size) / 2f
    val y = (height - size) / 2f
    if (x != 0f || y != 0f) {
        val matrix = Matrix().apply { setTranslate(-x, -y) }
        paint.shader.setLocalMatrix(matrix)
    }
    canvas.drawCircle(size / 2f, size / 2f, size / 2f, paint)
    return output
}

/**
 * 将 Bitmap 裁剪为圆角矩形。
 *
 * 使用 BitmapShader 实现，兼容硬件加速。
 * 调用方负责在不再需要时回收原始 Bitmap。
 *
 * @param radius 圆角半径（像素）
 */
fun Bitmap.toRounded(radius: Float): Bitmap {
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = BitmapShader(this@toRounded, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }
    val rect = android.graphics.RectF(0f, 0f, width.toFloat(), height.toFloat())
    canvas.drawRoundRect(rect, radius, radius, paint)
    return output
}

/**
 * 将 Bitmap 旋转指定角度。
 *
 * @param degrees 旋转角度（正值为顺时针）
 * @return 旋转后的新 Bitmap，调用方负责回收原始 Bitmap
 */
fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

/**
 * 压缩 Bitmap 到指定质量并保存到文件。
 *
 * @param file 目标文件
 * @param format 压缩格式，默认 PNG
 * @param quality 压缩质量 0-100，默认 80
 * @return 是否保存成功
 */
fun Bitmap.compressTo(
    file: File,
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG,
    quality: Int = 80
): Boolean {
    file.ensureParentDir()
    return try {
        FileOutputStream(file).use { fos ->
            compress(format, quality.coerceIn(0, 100), fos)
        }
        true
    } catch (_: Exception) {
        false
    }
}

private fun sampleSizeForBounds(width: Int, height: Int, maxWidth: Int, maxHeight: Int): Int {
    if (width <= 0 || height <= 0) return 1
    var inSampleSize = 1
    if (width > maxWidth || height > maxHeight) {
        var halfW = width / 2
        var halfH = height / 2
        while (halfW / inSampleSize >= maxWidth && halfH / inSampleSize >= maxHeight) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

/**
 * 计算 Bitmap 的采样大小，使解码后图片不超过指定最大宽高。
 *
 * @param options [BitmapFactory.Options]，需已执行 `inJustDecodeBounds = true` 的解码
 * @param maxWidth 最大宽度（像素）
 * @param maxHeight 最大高度（像素）
 * @return 采样大小（2 的幂）
 */
fun calculateSampleSize(
    options: BitmapFactory.Options,
    maxWidth: Int,
    maxHeight: Int
): Int = sampleSizeForBounds(options.outWidth, options.outHeight, maxWidth, maxHeight)

/**
 * 计算 Bitmap 的采样大小（兼容旧版 [BitmapFactoryOptionsCompat] 包装）。
 *
 * @param options 包含图片原始宽高信息
 * @param maxWidth 最大宽度
 * @param maxHeight 最大高度
 * @return 采样大小（2 的幂）
 */
fun calculateSampleSize(
    options: BitmapFactoryOptionsCompat,
    maxWidth: Int,
    maxHeight: Int
): Int = sampleSizeForBounds(options.outWidth, options.outHeight, maxWidth, maxHeight)

/**
 * 自适采样解码文件图片，降低 OOM 风险（大图为先缩小再解码）。
 *
 * @param maxWidth 允许的最大宽度（像素）
 * @param maxHeight 允许的最大高度（像素）
 */
fun File.decodeBitmapSampled(maxWidth: Int, maxHeight: Int): Bitmap? {
    val path = absolutePath
    val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
    BitmapFactory.decodeFile(path, opts)
    if (opts.outWidth <= 0 || opts.outHeight <= 0) return null
    opts.inSampleSize = calculateSampleSize(opts, maxWidth, maxHeight)
    opts.inJustDecodeBounds = false
    return BitmapFactory.decodeFile(path, opts)
}

/**
 * BitmapFactory.Options 的兼容包装，用于 [calculateSampleSize]。
 */
class BitmapFactoryOptionsCompat {
    var outWidth: Int = 0
    var outHeight: Int = 0
}
