package com.answufeng.utils.demo

import com.answufeng.utils.*

class DateFragment : BaseDemoFragment() {

    @OptIn(AwExperimentalApi::class)
    override fun setupDemo() {
        val now = System.currentTimeMillis()

        addTitle("日期格式化")
        addLog("默认格式: ${now.formatDate()}")
        addLog("自定义格式: ${now.formatDate("yyyy/MM/dd")}")

        addTitle("日期比较")
        addLog("是否今天: ${now.isToday()}")
        addLog("是否昨天: ${now.isYesterday()}")
        addLog("同一天: ${now.isSameDay(now - 1000L)}")

        addTitle("友好时间")
        addLog("刚刚: ${(now - 30_000L).toFriendlyTime()}")
        addLog("5分钟前: ${(now - 5 * 60_000L).toFriendlyTime()}")
        addLog("3小时前: ${(now - 3 * 3_600_000L).toFriendlyTime()}")
        addLog("昨天: ${(now - 30 * 3_600_000L).toFriendlyTime()}")

        addTitle("日期计算")
        addLog("今天开始: ${now.startOfDay().formatDate()}")
        addLog("今天结束: ${now.endOfDay().formatDate()}")
        addLog("加3天: ${now.addDays(3).formatDate()}")
        addLog("加2小时: ${now.addHours(2).formatDate()}")
        addLog("加30分钟: ${now.addMinutes(30).formatDate()}")

        addTitle("随机")
        addLog("随机字符串(8): ${randomString(8)}")
        addLog("随机数字(6): ${randomNumericString(6)}")
        addLog("随机字母(10): ${randomLetterString(10)}")
        addLog("随机整数(1-100): ${randomInt(1, 100)}")
        addLog("随机颜色: #${Integer.toHexString(randomColor()).uppercase()}")
        addLog("列表随机: ${listOf("Apple", "Banana", "Orange", "Grape").randomElement()}")
    }
}
