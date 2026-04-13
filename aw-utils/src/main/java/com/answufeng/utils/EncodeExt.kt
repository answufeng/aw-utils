@file:Suppress("unused")

package com.answufeng.utils

import android.util.Base64

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

/**
 * 将 Base64 字符串解码为字符串。
 *
 * @param flags Base64 标志位，默认 [Base64.NO_WRAP]
 */
fun String.decodeBase64String(flags: Int = Base64.NO_WRAP): String {
    return String(decodeBase64(flags))
}

/**
 * 将字节数组转换为小写十六进制字符串。
 *
 * 如 `[0x0F, 0xAB]` → `"0fab"`。
 */
fun ByteArray.toHexString(): String {
    return joinToString("") { "%02x".format(it) }
}

/**
 * 将十六进制字符串转换为字节数组。
 *
 * @throws IllegalArgumentException 长度不是偶数时抛出
 */
fun String.hexToByteArray(): ByteArray {
    require(length % 2 == 0) { "Hex string must have even length, got $length" }
    return chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}
