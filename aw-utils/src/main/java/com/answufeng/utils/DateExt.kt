package com.answufeng.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val FORMATTER_CACHE_MAX_SIZE = 8

private val threadLocalFormatters = object : ThreadLocal<LinkedHashMap<String, SimpleDateFormat>>() {
    override fun initialValue(): LinkedHashMap<String, SimpleDateFormat> =
        object : LinkedHashMap<String, SimpleDateFormat>(8, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, SimpleDateFormat>): Boolean {
                return size > FORMATTER_CACHE_MAX_SIZE
            }
        }
}

private fun calendarOf(timeInMillis: Long): Calendar = Calendar.getInstance().apply {
    this.timeInMillis = timeInMillis
}

private fun calendarNow(): Calendar = Calendar.getInstance()

private fun getFormatter(pattern: String): SimpleDateFormat {
    val cache = threadLocalFormatters.get()!!
    return cache.getOrPut(pattern) { SimpleDateFormat(pattern, Locale.US) }
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
@Deprecated(
    message = "Use System.currentTimeMillis() directly — this wrapper adds no value",
    level = DeprecationLevel.WARNING
)
fun currentTimeMillis(): Long = System.currentTimeMillis()

/**
 * 判断时间戳是否为今天。
 */
fun Long.isToday(): Boolean {
    val cal1 = calendarOf(this)
    val cal2 = calendarNow()
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

/**
 * 判断时间戳是否为昨天。
 */
fun Long.isYesterday(): Boolean {
    val cal1 = calendarOf(this)
    val cal2 = calendarNow().apply { add(Calendar.DAY_OF_YEAR, -1) }
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

/**
 * 判断两个时间戳是否为同一天。
 */
fun Long.isSameDay(other: Long): Boolean {
    val cal1 = calendarOf(this)
    val cal2 = calendarOf(other)
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}

/**
 * 将时间戳转换为友好的中文时间描述。
 *
 * 注意：输出为硬编码中文字符串，如需国际化请自行实现。
 *
 * 规则：
 * - < 1分钟 → "刚刚"
 * - < 1小时 → "X分钟前"
 * - < 24小时 → "X小时前"
 * - 昨天 → "昨天 HH:mm"
 * - 今年 → "MM-dd HH:mm"
 * - 更早 → "yyyy-MM-dd"
 */
@AwExperimentalApi
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
    val cal = calendarOf(this)
    val now = calendarNow()
    return cal.get(Calendar.YEAR) == now.get(Calendar.YEAR)
}

/**
 * 获取当天开始时间戳（00:00:00.000）。
 */
fun Long.startOfDay(): Long {
    return calendarOf(this).apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

/**
 * 获取当天结束时间戳（23:59:59.999）。
 */
fun Long.endOfDay(): Long {
    return calendarOf(this).apply {
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis
}

/**
 * 在当前时间戳上增加指定天数。
 *
 * 注意：使用纯毫秒计算，不处理夏令时（DST）切换。
 * 如需精确的日历运算，请使用 `Calendar.add()`。
 */
fun Long.addDays(days: Int): Long {
    return this + days.toLong() * 86_400_000L
}

/**
 * 在当前时间戳上增加指定小时数。
 *
 * 注意：使用纯毫秒计算，不处理夏令时（DST）切换。
 */
fun Long.addHours(hours: Int): Long {
    return this + hours.toLong() * 3_600_000L
}

/**
 * 在当前时间戳上增加指定分钟数。
 */
fun Long.addMinutes(minutes: Int): Long {
    return this + minutes.toLong() * 60_000L
}

/**
 * 在当前时间戳上增加指定秒数。
 */
fun Long.addSeconds(seconds: Int): Long {
    return this + seconds.toLong() * 1_000L
}

/**
 * 在当前时间戳上增加指定周数。
 */
fun Long.addWeeks(weeks: Int): Long {
    return this + weeks.toLong() * 604_800_000L
}

/**
 * 在当前时间戳上增加指定月数。
 *
 * 使用 [Calendar.add] 实现，正确处理月末和夏令时切换。
 * 如 `1月31日 + 1个月` → `2月28日`（或29日）。
 */
fun Long.addMonths(months: Int): Long {
    return calendarOf(this).apply { add(Calendar.MONTH, months) }.timeInMillis
}

/**
 * 在当前时间戳上增加指定年数。
 *
 * 使用 [Calendar.add] 实现，正确处理闰年。
 * 如 `2024-02-29 + 1年` → `2025-02-28`。
 */
fun Long.addYears(years: Int): Long {
    return calendarOf(this).apply { add(Calendar.YEAR, years) }.timeInMillis
}

/**
 * 将毫秒时长格式化为可读字符串。
 *
 * ```kotlin
 * 3723000L.formatDuration()          // "01:02:03"
 * 3723000L.formatDuration(showMs = true)  // "01:02:03.000"
 * 90000L.formatDuration()            // "01:30"
 * 3000L.formatDuration()             // "00:03"
 * ```
 *
 * 负值会被视为 0（输出 "00:00:00"）。
 *
 * @param showMs 是否显示毫秒部分，默认 false
 * @return 格式化后的时长字符串
 */
fun Long.formatDuration(showMs: Boolean = false): String {
    val ms = this.coerceAtLeast(0L)
    val seconds = ms / 1000
    val minutes = seconds / 60
    val hours = minutes / 60

    val h = hours.toString().padStart(2, '0')
    val m = (minutes % 60).toString().padStart(2, '0')
    val s = (seconds % 60).toString().padStart(2, '0')

    return if (showMs) {
        val millis = (ms % 1000).toString().padStart(3, '0')
        "$h:$m:$s.$millis"
    } else {
        "$h:$m:$s"
    }
}

/**
 * 将毫秒时长格式化为友好的中文描述。
 *
 * ```kotlin
 * 3723000L.formatDurationFriendly()  // "1小时2分钟3秒"
 * 90000L.formatDurationFriendly()    // "1分钟30秒"
 * 3000L.formatDurationFriendly()     // "3秒"
 * ```
 *
 * 负值会被视为 0（输出 "0秒"）。
 *
 * @return 中文友好的时长描述，零值返回 "0秒"
 */
fun Long.formatDurationFriendly(): String {
    val ms = this.coerceAtLeast(0L)
    val totalSeconds = ms / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    val parts = mutableListOf<String>()
    if (hours > 0) parts.add("${hours}小时")
    if (minutes > 0) parts.add("${minutes}分钟")
    if (seconds > 0 || parts.isEmpty()) parts.add("${seconds}秒")

    return parts.joinToString("")
}
