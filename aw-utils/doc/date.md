# DateExt — 日期格式化 / 比较 / 友好时间 / 日期计算

> 源码：[DateExt.kt](../src/main/java/com/answufeng/utils/DateExt.kt)

## 格式化与解析

```kotlin
val now = System.currentTimeMillis()

now.formatDate()                    // "2026-04-29 09:30:00"
now.formatDate("yyyy/MM/dd")       // "2026/04/29"
Date().format("HH:mm")             // "09:30"
"2026-04-29".parseDate("yyyy-MM-dd") // Date?
```

> 内部使用 ThreadLocal + LRU 缓存 `SimpleDateFormat`，线程安全。

## 比较

```kotlin
now.isToday()              // true
now.isYesterday()          // false
now.isSameDay(otherTs)     // Boolean
```

## 友好时间

```kotlin
@OptIn(AwExperimentalApi::class)
val friendly = timestamp.toFriendlyTime()
// < 1分钟 → "刚刚"
// < 1小时 → "5分钟前"
// < 24小时 → "3小时前"
// 昨天 → "昨天 18:30"
// 今年 → "04-28 18:30"
// 更早 → "2025-12-01"
```

> ⚠️ 输出为硬编码中文字符串，如需国际化请自行实现。

## 日期计算

```kotlin
now.startOfDay()      // 当天 00:00:00.000
now.endOfDay()        // 当天 23:59:59.999
now.addDays(3)        // +3 天
now.addHours(2)       // +2 小时
now.addMinutes(30)    // +30 分钟
now.addSeconds(10)    // +10 秒
now.addWeeks(1)       // +1 周
now.addMonths(3)     // +3 个月（Calendar.add，正确处理月末/DST）
now.addYears(1)      // +1 年（Calendar.add，正确处理闰年）
```

> ⚠️ `addDays`/`addHours`/`addMinutes`/`addSeconds`/`addWeeks` 为纯毫秒计算，不处理夏令时（DST）切换。`addMonths`/`addYears` 使用 `Calendar.add()` 实现，正确处理月末和闰年。

## 时长格式化

```kotlin
3723000L.formatDuration()               // "01:02:03"
3723000L.formatDuration(showMs = true)   // "01:02:03.000"
90000L.formatDuration()                  // "01:30"
3000L.formatDuration()                   // "00:03"

3723000L.formatDurationFriendly()        // "1小时2分钟3秒"
90000L.formatDurationFriendly()          // "1分钟30秒"
3000L.formatDurationFriendly()           // "3秒"
0L.formatDurationFriendly()              // "0秒"
```

> 负值会被视为 0。

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `currentTimeMillis()` | `System.currentTimeMillis()` |
