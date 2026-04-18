package com.answufeng.utils

import android.content.Context
import android.graphics.Bitmap
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

/**
 * 计算 Bitmap 的采样大小，使解码后图片不超过指定最大宽高。
 *
 * @param maxWidth 最大宽度
 * @param maxHeight 最大高度
 * @return 采样大小（2 的幂）
 */
fun calculateSampleSize(
    options: BitmapFactoryOptionsCompat,
    maxWidth: Int,
    maxHeight: Int
): Int {
    val width = options.outWidth
    val height = options.outHeight
    var inSampleSize = 1
    if (width > maxWidth || height > maxHeight) {
        val halfWidth = width / 2
        val halfHeight = height / 2
        while (halfWidth / inSampleSize >= maxWidth && halfHeight / inSampleSize >= maxHeight) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

/**
 * BitmapFactory.Options 的兼容包装，用于 [calculateSampleSize]。
 */
class BitmapFactoryOptionsCompat {
    var outWidth: Int = 0
    var outHeight: Int = 0
}
