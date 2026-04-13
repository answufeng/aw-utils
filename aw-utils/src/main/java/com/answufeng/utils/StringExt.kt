package com.answufeng.utils

import java.security.MessageDigest

private val PHONE_REGEX = Regex("^1[3-9]\\d{9}$")
private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val ID_CARD_REGEX = Regex("^\\d{17}[\\dXx]$")
private val URL_REGEX = Regex("^https?://[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:/~+#]*)*$")

fun String.isPhoneNumber(): Boolean = matches(PHONE_REGEX)

fun String.isEmail(): Boolean = matches(EMAIL_REGEX)

fun String.isIdCard(): Boolean = matches(ID_CARD_REGEX)

fun String.isDigitsOnly(): Boolean = all { it.isDigit() }

fun String.isUrl(): Boolean = matches(URL_REGEX)

fun String.maskPhone(): String {
    if (length != 11) return this
    return replaceRange(3, 7, "****")
}

fun String.maskIdCard(): String {
    if (length < 8) return this
    return replaceRange(4, length - 4, "*".repeat(length - 8))
}

fun String.maskEmail(): String {
    val atIndex = indexOf('@')
    if (atIndex <= 1) return this
    return "${first()}${"*".repeat(atIndex - 1)}${substring(atIndex)}"
}

fun String.md5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(toByteArray())
    return bytes.toHexString()
}

fun String.sha256(): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return bytes.toHexString()
}

fun String?.orDefault(default: String = ""): String {
    return if (isNullOrBlank()) default else this
}

fun String.ellipsize(maxLength: Int, suffix: String = "…"): String {
    return if (length <= maxLength) this else take(maxLength) + suffix
}

fun String?.isNotNullOrBlank(): Boolean = !isNullOrBlank()

fun String?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()
