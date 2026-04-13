package com.answufeng.utils

import org.junit.Assert.*
import org.junit.Test

class EncodeExtTest {

    @Test
    fun `toHexString produces correct output`() {
        assertEquals("0aff", byteArrayOf(0x0A, 0xFF.toByte()).toHexString())
    }

    @Test
    fun `toHexString of empty array`() {
        assertEquals("", byteArrayOf().toHexString())
    }

    @Test
    fun `toHexString of single byte`() {
        assertEquals("00", byteArrayOf(0x00).toHexString())
    }

    @Test
    fun `hexToByteArray decodes correctly`() {
        val result = "0aff".hexToByteArray()
        assertEquals(2, result.size)
        assertEquals(0x0A.toByte(), result[0])
        assertEquals(0xFF.toByte(), result[1])
    }

    @Test
    fun `hexToByteArray of empty string`() {
        assertEquals(0, "".hexToByteArray().size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `hexToByteArray throws on odd length`() {
        "0af".hexToByteArray()
    }

    @Test
    fun `hex roundtrip`() {
        val original = "Hello aw-utils".toByteArray()
        val hex = original.toHexString()
        val decoded = hex.hexToByteArray()
        assertArrayEquals(original, decoded)
    }

    @Test
    fun `toHexString is lowercase`() {
        assertEquals("0a", byteArrayOf(0x0A).toHexString())
    }
}
