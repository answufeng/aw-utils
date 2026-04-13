package com.answufeng.utils

import android.util.Base64

fun ByteArray.toBase64(flags: Int = Base64.NO_WRAP): String {
    return Base64.encodeToString(this, flags)
}

fun String.encodeBase64(flags: Int = Base64.NO_WRAP): String {
    return toByteArray().toBase64(flags)
}

fun String.decodeBase64(flags: Int = Base64.NO_WRAP): ByteArray {
    return Base64.decode(this, flags)
}

fun String.decodeBase64String(flags: Int = Base64.NO_WRAP): String {
    return String(decodeBase64(flags))
}

fun ByteArray.toHexString(): String {
    return joinToString("") { "%02x".format(it) }
}

fun String.hexToByteArray(): ByteArray {
    require(length % 2 == 0) { "Hex string must have even length, got $length" }
    return chunked(2).map { it.toInt(16).toByte() }.toByteArray()
}
