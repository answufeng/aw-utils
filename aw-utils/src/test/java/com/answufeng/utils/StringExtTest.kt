package com.answufeng.utils

import org.junit.Assert.*
import org.junit.Test

class StringExtTest {

    @Test
    fun `valid phone numbers`() {
        assertTrue("13812345678".isPhoneNumber())
        assertTrue("15912345678".isPhoneNumber())
        assertTrue("18612345678".isPhoneNumber())
        assertTrue("17012345678".isPhoneNumber())
        assertTrue("19912345678".isPhoneNumber())
    }

    @Test
    fun `invalid phone numbers`() {
        assertFalse("".isPhoneNumber())
        assertFalse("1381234567".isPhoneNumber())
        assertFalse("138123456789".isPhoneNumber())
        assertFalse("12345678901".isPhoneNumber())
        assertFalse("abc12345678".isPhoneNumber())
        assertFalse("038 1234 5678".isPhoneNumber())
    }

    @Test
    fun `valid emails`() {
        assertTrue("test@example.com".isEmail())
        assertTrue("user.name@domain.co.uk".isEmail())
        assertTrue("user+tag@example.org".isEmail())
    }

    @Test
    fun `invalid emails`() {
        assertFalse("".isEmail())
        assertFalse("not-an-email".isEmail())
        assertFalse("@example.com".isEmail())
        assertFalse("user@".isEmail())
    }

    @Test
    fun `valid id cards`() {
        assertTrue("110101199001011234".isIdCard())
        assertTrue("11010119900101123X".isIdCard())
        assertTrue("11010119900101123x".isIdCard())
    }

    @Test
    fun `invalid id cards`() {
        assertFalse("".isIdCard())
        assertFalse("11010119900101123".isIdCard())
        assertFalse("1101011990010112345".isIdCard())
    }

    @Test
    fun `maskPhone hides middle digits`() {
        assertEquals("138****5678", "13812345678".maskPhone())
    }

    @Test
    fun `maskPhone returns original for short strings`() {
        assertEquals("123", "123".maskPhone())
        assertEquals("1234567", "1234567".maskPhone())
    }

    @Test
    fun `maskIdCard hides middle digits`() {
        assertEquals("1101**********1234", "110101199001011234".maskIdCard())
    }

    @Test
    fun `maskIdCard returns original for short strings`() {
        assertEquals("1234567", "1234567".maskIdCard())
    }

    @Test
    fun `maskEmail hides local part`() {
        assertEquals("h****@example.com", "hello@example.com".maskEmail())
    }

    @Test
    fun `maskEmail returns original for short local part`() {
        assertEquals("a@b.com", "a@b.com".maskEmail())
    }

    @Test
    fun `maskEmail returns original for no at sign`() {
        assertEquals("noemail", "noemail".maskEmail())
    }

    @Test
    fun `md5 produces correct hash`() {
        assertEquals("5d41402abc4b2a76b9719d911017c592", "hello".md5())
    }

    @Test
    fun `md5 of empty string`() {
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", "".md5())
    }

    @Test
    fun `sha256 produces correct hash`() {
        assertEquals(
            "2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824",
            "hello".sha256()
        )
    }

    @Test
    fun `sha256 of empty string`() {
        assertEquals(
            "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
            "".sha256()
        )
    }

    @Test
    fun `valid urls`() {
        assertTrue("https://example.com".isUrl())
        assertTrue("http://example.com/path".isUrl())
        assertTrue("https://sub.domain.com/path?q=1&a=2".isUrl())
    }

    @Test
    fun `invalid urls`() {
        assertFalse("".isUrl())
        assertFalse("not-a-url".isUrl())
        assertFalse("ftp://example.com".isUrl())
        assertFalse("example.com".isUrl())
    }

    @Test
    fun `isDigitsOnly`() {
        assertTrue("12345".isDigitsOnly())
        assertTrue("".isDigitsOnly())
        assertFalse("12a45".isDigitsOnly())
    }

    @Test
    fun `orDefault returns value when not blank`() {
        assertEquals("hello", "hello".orDefault("default"))
    }

    @Test
    fun `orDefault returns default when null or blank`() {
        assertEquals("default", (null as String?).orDefault("default"))
        assertEquals("default", "".orDefault("default"))
        assertEquals("default", "   ".orDefault("default"))
    }

    @Test
    fun `orDefault default value`() {
        assertEquals("", (null as String?).orDefault())
    }

    @Test
    fun `ellipsize truncates long strings`() {
        assertEquals("Hello…", "Hello World".ellipsize(5))
        assertEquals("Hi", "Hi".ellipsize(5))
    }

    @Test
    fun `ellipsize with custom suffix`() {
        assertEquals("Hello...", "Hello World".ellipsize(5, "..."))
    }

    @Test
    fun `isNotNullOrBlank`() {
        assertTrue("hello".isNotNullOrBlank())
        assertFalse((null as String?).isNotNullOrBlank())
        assertFalse("".isNotNullOrBlank())
        assertFalse("   ".isNotNullOrBlank())
    }

    @Test
    fun `isNotNullOrEmpty`() {
        assertTrue("hello".isNotNullOrEmpty())
        assertFalse((null as String?).isNotNullOrEmpty())
        assertFalse("".isNotNullOrEmpty())
        assertTrue("   ".isNotNullOrEmpty())
    }
}
