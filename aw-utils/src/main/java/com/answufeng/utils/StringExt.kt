package com.answufeng.utils

import java.nio.charset.Charset
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
 *
 * @param charset 字符编码，默认 UTF-8
 */
fun String.md5(charset: Charset = Charsets.UTF_8): String {
    val bytes = MessageDigest.getInstance("MD5").digest(toByteArray(charset))
    return bytes.toHexString()
}

/**
 * 计算字符串的 SHA-256 摘要（64 位小写十六进制）。
 *
 * @param charset 字符编码，默认 UTF-8
 */
fun String.sha256(charset: Charset = Charsets.UTF_8): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(toByteArray(charset))
    return bytes.toHexString()
}

@Deprecated(
    message = "Use Elvis operator ?: instead, e.g. str ?: default",
    level = DeprecationLevel.WARNING
)
fun String?.orDefault(default: String = ""): String {
    return if (isNullOrBlank()) default else this
}

@Deprecated(
    message = "Use truncate() to avoid confusion with TextUtils.ellipsize()",
    replaceWith = ReplaceWith("truncate(maxLength, suffix)")
)
fun String.ellipsize(maxLength: Int, suffix: String = "…"): String = truncate(maxLength, suffix)

/**
 * 截断超长字符串并添加后缀。
 *
 * @param maxLength 最大保留长度，必须 >= 0
 * @param suffix 截断后缀，默认为 `"…"`
 */
fun String.truncate(maxLength: Int, suffix: String = "…"): String {
    require(maxLength >= 0) { "maxLength must be >= 0, got $maxLength" }
    return if (length <= maxLength) this else take(maxLength) + suffix
}

@Deprecated(
    message = "Use !isNullOrBlank() directly — this extension adds no value over the stdlib",
    level = DeprecationLevel.WARNING
)
fun String?.isNotNullOrBlank(): Boolean = !isNullOrBlank()

@Deprecated(
    message = "Use !isNullOrEmpty() directly — this extension adds no value over the stdlib",
    level = DeprecationLevel.WARNING
)
fun String?.isNotNullOrEmpty(): Boolean = !isNullOrEmpty()
