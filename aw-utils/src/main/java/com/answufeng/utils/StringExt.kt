package com.answufeng.utils

import java.security.MessageDigest

private val PHONE_REGEX = Regex("^1[3-9]\\d{9}$")
private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val ID_CARD_REGEX = Regex("^\\d{17}[\\dXx]$")
private val URL_REGEX = Regex("^https?://[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:/~+#]*)*$")

/**
 * 判断字符串是否为合法的中国大陆手机号。
 *
 * 规则：1 开头，第二位 3-9，共 11 位数字。
 */
fun String.isPhoneNumber(): Boolean = matches(PHONE_REGEX)

/**
 * 判断字符串是否为合法的邮箱地址。
 */
fun String.isEmail(): Boolean = matches(EMAIL_REGEX)

/**
 * 判断字符串是否为合法的中国大陆身份证号（18 位）。
 */
fun String.isIdCard(): Boolean = matches(ID_CARD_REGEX)

/**
 * 判断字符串是否仅包含数字。
 */
fun String.isDigitsOnly(): Boolean = all { it.isDigit() }

/**
 * 判断字符串是否为合法的 URL（http/https）。
 */
fun String.isUrl(): Boolean = matches(URL_REGEX)

/**
 * 手机号脱敏，将中间四位替换为 `****`。
 *
 * 如 `"13812345678"` → `"138****5678"`。
 * 非 11 位字符串原样返回。
 */
fun String.maskPhone(): String {
    if (length != 11) return this
    return replaceRange(3, 7, "****")
}

/**
 * 身份证号脱敏，保留前 4 位和后 4 位，中间用 `*` 替换。
 *
 * 长度不足 8 位的字符串原样返回。
 */
fun String.maskIdCard(): String {
    if (length < 8) return this
    return replaceRange(4, length - 4, "*".repeat(length - 8))
}

/**
 * 邮箱脱敏，保留首字符和 `@` 及其后部分，中间用 `*` 替换。
 *
 * 如 `"hello@example.com"` → `"h****@example.com"`。
 */
fun String.maskEmail(): String {
    val atIndex = indexOf('@')
    if (atIndex <= 1) return this
    return "${first()}${"*".repeat(atIndex - 1)}${substring(atIndex)}"
}

/**
 * 计算字符串的 MD5 摘要（32 位小写十六进制）。
 */
fun String.md5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(toByteArray())
    return bytes.toHexString()
}

/**
 * 计算字符串的 SHA-256 摘要（64 位小写十六进制）。
 */
fun String.sha256(): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(toByteArray())
    return bytes.toHexString()
}

/**
 * 若字符串为 null 或空白，返回默认值。
 *
 * @param default 默认值，默认为空字符串
 */
fun String?.orDefault(default: String = ""): String {
    return if (isNullOrBlank()) default else this
}

/**
 * 截断超长字符串并添加后缀。
 *
 * @param maxLength 最大保留长度
 * @param suffix 截断后缀，默认为 `"…"`
 */
fun String.ellipsize(maxLength: Int, suffix: String = "…"): String {
    return if (length <= maxLength) this else take(maxLength) + suffix
}

/**
 * 判断字符串是否非 null 且非空白。
 */
fun String?.isNotNullOrBlank(): Boolean = !isNullOrBlank()

/**
 * 判断字符串是否非 null 且非空。
 */
fun String?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()
