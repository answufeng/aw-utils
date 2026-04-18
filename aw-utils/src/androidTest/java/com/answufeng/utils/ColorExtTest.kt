package com.answufeng.utils

import android.graphics.Color
import org.junit.Assert.*
import org.junit.Test

class ColorExtTest {

    @Test
    fun toHexColor_returnsCorrectFormat() {
        val color = Color.argb(255, 31, 113, 179)
        assertEquals("#FF1F71B3", color.toHexColor())
    }

    @Test
    fun toHexColorNoAlpha_excludesAlpha() {
        val color = Color.argb(128, 31, 113, 179)
        assertEquals("#1F71B3", color.toHexColorNoAlpha())
    }

    @Test
    fun toColorInt_parses6DigitHex() {
        val color = "#1F71B3".toColorInt()
        assertNotNull(color)
        assertEquals(Color.argb(255, 31, 113, 179), color)
    }

    @Test
    fun toColorInt_parses8DigitHex() {
        val color = "#801F71B3".toColorInt()
        assertNotNull(color)
        assertEquals(Color.argb(128, 31, 113, 179), color)
    }

    @Test
    fun toColorInt_parses3DigitHex() {
        val color = "#F00".toColorInt()
        assertNotNull(color)
        assertEquals(Color.argb(255, 255, 0, 0), color)
    }

    @Test
    fun toColorInt_parses4DigitHex() {
        val color = "#8F00".toColorInt()
        assertNotNull(color)
        assertEquals(Color.argb(136, 255, 0, 0), color)
    }

    @Test
    fun toColorInt_returnsNullForInvalid() {
        assertNull("#XYZ".toColorInt())
        assertNull("invalid".toColorInt())
        assertNull("#12".toColorInt())
    }

    @Test
    fun withAlpha_changesAlpha() {
        val color = Color.argb(255, 100, 100, 100)
        val result = color.withAlpha(128)
        assertEquals(128, Color.alpha(result))
        assertEquals(100, Color.red(result))
        assertEquals(100, Color.green(result))
        assertEquals(100, Color.blue(result))
    }

    @Test
    fun blend_mixesTwoColors() {
        val red = Color.argb(255, 255, 0, 0)
        val blue = Color.argb(255, 0, 0, 255)
        val result = red.blend(blue, 0.5f)
        assertEquals(255, Color.alpha(result))
        assertEquals(128, Color.red(result))
        assertEquals(0, Color.green(result))
        assertEquals(128, Color.blue(result))
    }

    @Test
    fun blend_atRatio0_returnsFirstColor() {
        val red = Color.argb(255, 255, 0, 0)
        val blue = Color.argb(255, 0, 0, 255)
        val result = red.blend(blue, 0f)
        assertEquals(255, Color.red(result))
        assertEquals(0, Color.blue(result))
    }

    @Test
    fun blend_atRatio1_returnsSecondColor() {
        val red = Color.argb(255, 255, 0, 0)
        val blue = Color.argb(255, 0, 0, 255)
        val result = red.blend(blue, 1f)
        assertEquals(0, Color.red(result))
        assertEquals(255, Color.blue(result))
    }
}
