package com.answufeng.utils

import org.junit.Assert.*
import org.junit.Test

@OptIn(AwExperimentalApi::class)
class DateExtTest {

    @Test
    fun `formatDate produces correct format`() {
        val formatted = System.currentTimeMillis().formatDate("yyyy")
        assertEquals("2026", formatted)
    }

    @Test
    fun `formatDate default pattern includes time`() {
        val formatted = System.currentTimeMillis().formatDate()
        assertTrue(formatted.contains(" "))
        assertTrue(formatted.contains("-"))
        assertTrue(formatted.contains(":"))
    }

    @Test
    fun `parseDate with valid input`() {
        val date = "2026-04-03".parseDate("yyyy-MM-dd")
        assertNotNull(date)
    }

    @Test
    fun `parseDate with invalid input returns null`() {
        val date = "not-a-date".parseDate("yyyy-MM-dd")
        assertNull(date)
    }

    @Test
    fun `isToday returns true for current time`() {
        assertTrue(System.currentTimeMillis().isToday())
    }

    @Test
    fun `isToday returns false for yesterday`() {
        assertFalse((System.currentTimeMillis() - 86_400_000L * 2).isToday())
    }

    @Test
    fun `isYesterday returns true for yesterday timestamp`() {
        assertTrue((System.currentTimeMillis() - 86_400_000L).isYesterday())
    }

    @Test
    fun `isYesterday returns false for today`() {
        assertFalse(System.currentTimeMillis().isYesterday())
    }

    @Test
    fun `isSameDay returns true for same day timestamps`() {
        val now = System.currentTimeMillis()
        assertTrue(now.isSameDay(now - 1000L))
    }

    @Test
    fun `isSameDay returns false for different days`() {
        val now = System.currentTimeMillis()
        assertFalse(now.isSameDay(now - 86_400_000L * 2))
    }

    @Test
    fun `toFriendlyTime shows gang gang for recent`() {
        val now = System.currentTimeMillis()
        assertEquals("刚刚", (now - 30_000L).toFriendlyTime())
    }

    @Test
    fun `toFriendlyTime shows minutes ago`() {
        val now = System.currentTimeMillis()
        assertTrue((now - 5 * 60_000L).toFriendlyTime().contains("分钟前"))
    }

    @Test
    fun `toFriendlyTime shows hours ago`() {
        val now = System.currentTimeMillis()
        assertTrue((now - 3 * 3_600_000L).toFriendlyTime().contains("小时前"))
    }

    @Test
    fun `toFriendlyTime shows yesterday`() {
        val now = System.currentTimeMillis()
        val result = (now - 30 * 3_600_000L).toFriendlyTime()
        assertTrue(result.startsWith("昨天"))
    }

    @Test
    fun `toFriendlyTime shows date for future`() {
        val future = System.currentTimeMillis() + 172_800_000L
        val result = future.toFriendlyTime()
        assertTrue(result.contains("-"))
    }

    @Suppress("DEPRECATION")
    @Test
    fun `currentTimeMillis returns positive value`() {
        assertTrue(currentTimeMillis() > 0)
    }
}
