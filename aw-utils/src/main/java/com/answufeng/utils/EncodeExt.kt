package com.answufeng.utils

import android.util.Base64
import android.text.Html
import android.text.TextUtils
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.Charset

/**
 * 将字节数组编码为 Base64 字符串。
 *
 * @param flags Base64 标志位，默认 [Base64.NO_WRAP]
 */
fun ByteArray.toBase64(flags: Int = Base64.NO_WRAP): String {
    return Base64.encodeToString(this, flags)
}

/**
 * 将字符串编码为 Base64。
 *
 * @param flags Base64 标志位，默认 [Base64.NO_WRAP]
 */
fun String.encodeBase64(flags: Int = Base64.NO_WRAP): String {
    return toByteArray().toBase64(flags)
}

/**
 * 将 Base64 字符串解码为字节数组。
 *
 * @param flags Base64 标志位，默认 [Base64.NO_WRAP]
 */
fun String.decodeBase64(flags: Int = Base64.NO_WRAP): ByteArray {
    return Base64.decode(this, flags)
}

@Deprecated(
    message = "Use decodeBase64ToString() for clearer naming",
    replaceWith = ReplaceWith("decodeBase64ToString(flags = flags)")
)
fun String.decodeBase64String(flags: Int = Base64.NO_WRAP): String {
    return decodeBase64ToString(flags = flags)
}

/**
 * 将 Base64 字符串解码为字符串。
 *
 * @param charset 字符编码，默认 UTF-8
 * @param flags Base64 标志位，默认 [Base64.NO_WRAP]
 */
fun String.decodeBase64ToString(charset: Charset = Charsets.UTF_8, flags: Int = Base64.NO_WRAP): String {
    return String(decodeBase64(flags), charset)
}

private val HEX_CHARS = charArrayOf(
    '0', '1', '2', '3', '4', '5', '6', '7',
    '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
)

/**
 * 将字节数组转换为小写十六进制字符串（查表法高性能实现）。
 *
 * 如 `[0x0F, 0xAB]` → `"0fab"`。
 */
fun ByteArray.toHexString(): String {
    if (isEmpty()) return ""
    val hex = CharArray(size * 2)
    for (i in indices) {
        val b = this[i].toInt() and 0xFF
        hex[i * 2] = HEX_CHARS[b shr 4]
        hex[i * 2 + 1] = HEX_CHARS[b and 0x0F]
    }
    return String(hex)
}

/**
 * 将十六进制字符串转换为字节数组。
 *
 * @throws IllegalArgumentException 长度不是偶数或包含非法字符时抛出
 */
fun String.hexToByteArray(): ByteArray {
    require(length % 2 == 0) { "Hex string must have even length, got $length" }
    val result = ByteArray(length / 2)
    for (i in result.indices) {
        val high = Character.digit(this[i * 2], 16)
        val low = Character.digit(this[i * 2 + 1], 16)
        require(high != -1 && low != -1) { "Invalid hex character at index ${i * 2}" }
        result[i] = ((high shl 4) or low).toByte()
    }
    return result
}

/**
 * URL 编码（UTF-8）。
 *
 * 如 `"hello world"` → `"hello+world"`，`"你好"` → `"%E4%BD%A0%E5%A5%BD"`。
 *
 * @param charset 编码字符集，默认 UTF-8
 */
fun String.urlEncode(charset: String = "UTF-8"): String {
    return try {
        URLEncoder.encode(this, charset)
    } catch (_: UnsupportedEncodingException) {
        this
    }
}

/**
 * URL 解码（UTF-8）。
 *
 * 如 `"hello+world"` → `"hello world"`，`"%E4%BD%A0%E5%A5%BD"` → `"你好"`。
 *
 * @param charset 解码字符集，默认 UTF-8
 */
fun String.urlDecode(charset: String = "UTF-8"): String {
    return try {
        URLDecoder.decode(this, charset)
    } catch (_: UnsupportedEncodingException) {
        this
    }
}

/**
 * HTML 编码，将特殊字符转义为 HTML 实体。
 *
 * 如 `"<b>hi</b>"` → `"&lt;b&gt;hi&lt;/b&gt;"`，
 * `"a & b"` → `"a &amp; b"`。
 */
fun String.htmlEncode(): String {
    return TextUtils.htmlEncode(this)
}

/**
 * HTML 解码，将 HTML 实体还原为原始字符。
 *
 * 如 `"&lt;b&gt;"` → `"<b>"`，
 * `"&amp;"` → `"&"`。
 *
 * 支持 Named entity（`&amp;`、`&lt;` 等）和 Numeric entity（`&#60;`、`&#x3c;`）。
 */
fun String.htmlDecode(): String {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this).toString()
    }
}
