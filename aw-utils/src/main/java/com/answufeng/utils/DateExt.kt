@file:Suppress("unused")

package com.answufeng.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val threadLocalFormatters = object : ThreadLocal<MutableMap<String, SimpleDateFormat>>() {
    override fun initialValue(): MutableMap<String, SimpleDateFormat> = mutableMapOf()
}

private fun getFormatter(pattern: String): SimpleDateFormat {
    val cache = threadLocalFormatters.get()!!
    return cache.getOrPut(pattern) { SimpleDateFormat(pattern, Locale.getDefault()) }
}

/**
 * 将时间戳格式化为日期字符串。
 *
 * @param pattern 日期格式，默认 `"yyyy-MM-dd HH:mm:ss"`
 */
fun Long.formatDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return getFormatter(pattern).format(Date(this))
}

/**
 * 将 [Date] 格式化为日期字符串。
 *
 * @param pattern 日期格式，默认 `"yyyy-MM-dd HH:mm:ss"`
 */
fun Date.format(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
    return getFormatter(pattern).format(this)
}

/**
 * 将日期字符串解析为 [Date]。
 *
 * @param pattern 日期格式，默认 `"yyyy-MM-dd HH:mm:ss"`
 * @return 解析成功返回 [Date]，失败返回 null
 */
fun String.parseDate(pattern: String = "yyyy-MM-dd HH:mm:ss"): Date? {
    return try {
        getFormatter(pattern).parse(this)
    } catch (_: Exception) {
        null
    }
}

/**
 * 获取当前时间戳（毫秒）。
 */
fun currentTimeMillis(): Long = System.currentTimeMillis()

/**
 * 判断时间戳是否为今天。
 */
fun Long.isToday(): Boolean {
    val cal1 = Calendar.getInstance().apply { timeInMillis = this@isToday }
    val cal2 = Calendar.getInstance()
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

/**
 * 判断时间戳是否为昨天。
 */
fun Long.isYesterday(): Boolean {
    val cal1 = Calendar.getInstance().apply { timeInMillis = this@isYesterday }
    val cal2 = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

/**
 * 判断两个时间戳是否为同一天。
 */
fun Long.isSameDay(other: Long): Boolean {
    val cal1 = Calendar.getInstance().apply { timeInMillis = this@isSameDay }
    val cal2 = Calendar.getInstance().apply { timeInMillis = other }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

/**
 * 将时间戳转换为友好的中文时间描述。
 *
 * 规则：
 * - < 1分钟 → "刚刚"
 * - < 1小时 → "X分钟前"
 * - < 24小时 → "X小时前"
 * - 昨天 → "昨天 HH:mm"
 * - 今年 → "MM-dd HH:mm"
 * - 更早 → "yyyy-MM-dd"
 */
fun Long.toFriendlyTime(): String {
    val now = System.currentTimeMillis()
    val diff = now - this
    return when {
        diff < 0L -> formatDate("yyyy-MM-dd")
        diff < 60_000L -> "刚刚"
        diff < 3_600_000L -> "${diff / 60_000L}分钟前"
        diff < 86_400_000L -> "${diff / 3_600_000L}小时前"
        diff < 172_800_000L -> "昨天 ${formatDate("HH:mm")}"
        this.isThisYear() -> formatDate("MM-dd HH:mm")
        else -> formatDate("yyyy-MM-dd")
    }
}

private fun Long.isThisYear(): Boolean {
    val cal = Calendar.getInstance().apply { timeInMillis = this@isThisYear }
    val now = Calendar.getInstance()
    return cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)
}
