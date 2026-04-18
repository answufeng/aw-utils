package com.answufeng.utils

import android.graphics.Color
import kotlin.math.roundToInt

/**
 * 将颜色 Int 转换为十六进制字符串（含透明度）。
 *
 * 如 `-14742431` → `"FF1F71B3"`
 */
fun Int.toHexColor(): String {
    return String.format("#%08X", this)
}

/**
 * 将颜色 Int 转换为十六进制字符串（不含透明度）。
 *
 * 如 `-14742431` → `"#1F71B3"`
 */
fun Int.toHexColorNoAlpha(): String {
    return String.format("#%06X", this and 0xFFFFFF)
}

/**
 * 将十六进制颜色字符串转换为 Int。
 *
 * 支持格式：`"#RGB"`、`"#ARGB"`、`"#RRGGBB"`、`"#AARRGGBB"`（`#` 可选）。
 *
 * @return 颜色 Int，解析失败返回 null
 */
fun String.toColorInt(): Int? {
    return try {
        val hex = removePrefix("#")
        val color = when (hex.length) {
            3 -> {
                val r = hex[0].toString().repeat(2)
                val g = hex[1].toString().repeat(2)
                val b = hex[2].toString().repeat(2)
                "FF$r$g$b".toLong(16).toInt()
            }
            4 -> {
                val a = hex[0].toString().repeat(2)
                val r = hex[1].toString().repeat(2)
                val g = hex[2].toString().repeat(2)
                val b = hex[3].toString().repeat(2)
                "$a$r$g$b".toLong(16).toInt()
            }
            6 -> ("FF$hex").toLong(16).toInt()
            8 -> hex.toLong(16).toInt()
            else -> return null
        }
        color
    } catch (_: Exception) {
        null
    }
}

/**
 * 调整颜色透明度。
 *
 * @param alpha 透明度 0-255
 */
fun Int.withAlpha(alpha: Int): Int {
    return Color.argb(
        alpha.coerceIn(0, 255),
        Color.red(this),
        Color.green(this),
        Color.blue(this)
    )
}

/**
 * 混合两种颜色。
 *
 * @param color2 第二种颜色
 * @param ratio 混合比例 0.0-1.0，0.0 完全为当前颜色，1.0 完全为 color2
 */
fun Int.blend(color2: Int, ratio: Float): Int {
    val r = ratio.coerceIn(0f, 1f)
    val inverse = 1f - r
    return Color.argb(
        (Color.alpha(this) * inverse + Color.alpha(color2) * r).roundToInt(),
        (Color.red(this) * inverse + Color.red(color2) * r).roundToInt(),
        (Color.green(this) * inverse + Color.green(color2) * r).roundToInt(),
        (Color.blue(this) * inverse + Color.blue(color2) * r).roundToInt()
    )
}
