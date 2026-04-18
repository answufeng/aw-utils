package com.answufeng.utils

import java.nio.charset.Charset
import java.security.MessageDigest

private val PHONE_REGEX = Regex("^1[3-9]\\d{9}$")
private val CHINESE_PHONE_REGEX = Regex("^1(3[0-9]|4[014578]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\\d{8}$")
private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
private val ID_CARD_REGEX = Regex("^\\d{17}[\\dXx]$")
private val URL_REGEX = Regex("^https?://[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:/~+#]*)*$")
private val BANK_CARD_REGEX = Regex("^\\d{16,19}$")

/**
 * 判断字符串是否为合法的中国大陆手机号。
 *
 * 规则：1 开头，第二位 3-9，共 11 位数字。
 */
fun String.isPhoneNumber(): Boolean = matches(PHONE_REGEX)

/**
 * 判断字符串是否为合法的中国大陆手机号（严格号段校验）。
 *
 * 在 [isPhoneNumber] 基础上增加号段验证，
 * 能排除无效号段（如 1400、1500 等未分配号段）。
 *
 * 当前覆盖号段：13x、14x（014578）、15x（0-35-9）、16x（2567）、17x（0-8）、18x、19x（0-35-9）。
 * 号段信息可能随运营商分配变化，请定期更新。
 */
fun String.isChinesePhoneNumber(): Boolean = matches(CHINESE_PHONE_REGEX)

/**
 * 判断字符串是否为合法的邮箱地址。
 */
fun String.isEmail(): Boolean = matches(EMAIL_REGEX)

private val ID_CARD_WEIGHTS = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
private const val ID_CARD_CHECK_CODES = "10X98765432"

/**
 * 判断字符串是否为合法的中国大陆身份证号（18 位）。
 *
 * 包含第 18 位校验码验证（加权因子算法），
 * 不再仅做格式匹配，能有效排除随机数字组合。
 */
fun String.isIdCard(): Boolean {
    if (!matches(ID_CARD_REGEX)) return false
    var sum = 0
    for (i in 0..16) {
        sum += (this[i] - '0') * ID_CARD_WEIGHTS[i]
    }
    val expectedCheckChar = ID_CARD_CHECK_CODES[sum % 11]
    return this[17].uppercaseChar() == expectedCheckChar
}

/**
 * 判断字符串是否仅包含数字。
 */
fun String.isDigitsOnly(): Boolean = all { it.isDigit() }

/**
 * 判断字符串是否为合法的 URL（http/https）。
 */
fun String.isUrl(): Boolean = matches(URL_REGEX)

/**
 * 判断字符串是否为合法的银行卡号（16-19 位数字）。
 */
fun String.isBankCard(): Boolean = matches(BANK_CARD_REGEX)

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
 * 银行卡号脱敏，保留前 4 位和后 4 位，中间用 `*` 替换。
 *
 * 如 `"6222021234567890"` → `"6222********7890"`。
 * 长度不足 8 位的字符串原样返回。
 */
fun String.maskBankCard(): String {
    if (length < 8) return this
    return replaceRange(4, length - 4, "*".repeat(length - 8))
}

/**
 * 通用脱敏方法，保留首尾指定数量的字符，中间用占位符替换。
 *
 * ```kotlin
 * "13812345678".mask(3, 4)           // "138****5678"
 * "110101199001011237".mask(4, 4)    // "1101**********1237"
 * "hello@example.com".mask(1, 10)    // "h**********mple.com"
 * ```
 *
 * @param keepStart 保留开头字符数
 * @param keepEnd 保留结尾字符数
 * @param maskChar 占位字符，默认 `*`
 * @return 脱敏后的字符串
 */
fun String.mask(keepStart: Int, keepEnd: Int, maskChar: Char = '*'): String {
    if (length <= keepStart + keepEnd) return this
    val maskedLength = length - keepStart - keepEnd
    return take(keepStart) + maskChar.toString().repeat(maskedLength) + takeLast(keepEnd)
}

private val threadLocalDigests = object : ThreadLocal<MutableMap<String, MessageDigest>>() {
    override fun initialValue(): MutableMap<String, MessageDigest> = mutableMapOf()
}

private fun getDigest(algorithm: String): MessageDigest {
    val cache = threadLocalDigests.get()!!
    return cache.getOrPut(algorithm) { MessageDigest.getInstance(algorithm) }
}

/**
 * 计算字符串的 MD5 摘要（32 位小写十六进制）。
 *
 * @param charset 字符编码，默认 UTF-8
 */
fun String.md5(charset: Charset = Charsets.UTF_8): String {
    val digest = getDigest("MD5")
    digest.reset()
    val bytes = digest.digest(toByteArray(charset))
    return bytes.toHexString()
}

/**
 * 计算字符串的 SHA-256 摘要（64 位小写十六进制）。
 *
 * @param charset 字符编码，默认 UTF-8
 */
fun String.sha256(charset: Charset = Charsets.UTF_8): String {
    val digest = getDigest("SHA-256")
    digest.reset()
    val bytes = digest.digest(toByteArray(charset))
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
