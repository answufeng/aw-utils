package com.answufeng.utils

import android.graphics.Color
import kotlin.random.Random

private const val ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
private const val NUMERIC = "0123456789"
private const val LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
private const val UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
private const val LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz"

/**
 * 生成指定长度的随机字母数字字符串。
 */
fun randomString(length: Int): String {
    require(length >= 0) { "Length must be >= 0, got $length" }
    return (1..length).map { ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)] }.joinToString("")
}

/**
 * 生成指定长度的随机数字字符串。
 */
fun randomNumericString(length: Int): String {
    require(length >= 0) { "Length must be >= 0, got $length" }
    return (1..length).map { NUMERIC[Random.nextInt(NUMERIC.length)] }.joinToString("")
}

/**
 * 生成指定长度的随机字母字符串。
 */
fun randomLetterString(length: Int): String {
    require(length >= 0) { "Length must be >= 0, got $length" }
    return (1..length).map { LETTERS[Random.nextInt(LETTERS.length)] }.joinToString("")
}

/**
 * 生成指定范围的随机整数。
 */
fun randomInt(min: Int, max: Int): Int {
    require(min <= max) { "min must be <= max, got min=$min max=$max" }
    return Random.nextInt(min, max + 1)
}

/**
 * 生成指定范围的随机长整数。
 */
fun randomLong(min: Long, max: Long): Long {
    require(min <= max) { "min must be <= max, got min=$min max=$max" }
    return Random.nextLong(min, max + 1)
}

/**
 * 生成随机颜色（带可选透明度）。
 *
 * @param alpha 透明度 0-255，默认 255（不透明）
 */
fun randomColor(alpha: Int = 255): Int {
    return Color.argb(
        alpha.coerceIn(0, 255),
        Random.nextInt(256),
        Random.nextInt(256),
        Random.nextInt(256)
    )
}

/**
 * 从列表中随机选择一个元素。
 */
fun <T> List<T>.randomElement(): T {
    require(isNotEmpty()) { "List must not be empty" }
    return this[Random.nextInt(size)]
}

/**
 * 从列表中随机选择 n 个不重复的元素。
 */
fun <T> List<T>.randomElements(n: Int): List<T> {
    require(n <= size) { "Cannot pick $n elements from list of size $size" }
    return shuffled().take(n)
}
