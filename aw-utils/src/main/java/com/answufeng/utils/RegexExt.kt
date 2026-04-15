package com.answufeng.utils

private val IP_REGEX = Regex("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$")
private val CHINESE_REGEX = Regex("^[\\u4e00-\\u9fa5]+$")
private val USERNAME_REGEX = Regex("^[a-zA-Z]\\w{5,19}$")
private val STRONG_PASSWORD_REGEX = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")
private val MEDIUM_PASSWORD_REGEX = Regex("^(?=.*[a-zA-Z])(?=.*\\d).{6,}$")
private val CHINESE_ID_NAME_REGEX = Regex("^[\\u4e00-\\u9fa5]{2,6}$")
private val CAR_PLATE_REGEX = Regex("^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤川青藏琼宁使领][A-Z][A-Z0-9]{5,6}$")
private val MAC_ADDRESS_REGEX = Regex("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$")

/**
 * 判断字符串是否为合法的 IPv4 地址。
 */
fun String.isIP(): Boolean = matches(IP_REGEX)

/**
 * 判断字符串是否仅包含中文字符。
 */
fun String.isChinese(): Boolean = matches(CHINESE_REGEX)

/**
 * 判断字符串是否为合法的用户名（字母开头，6-20 位字母数字下划线）。
 */
fun String.isUsername(): Boolean = matches(USERNAME_REGEX)

/**
 * 判断字符串是否为强密码（至少 8 位，包含大小写字母、数字和特殊字符）。
 */
fun String.isStrongPassword(): Boolean = matches(STRONG_PASSWORD_REGEX)

/**
 * 判断字符串是否为中等强度密码（至少 6 位，包含字母和数字）。
 */
fun String.isMediumPassword(): Boolean = matches(MEDIUM_PASSWORD_REGEX)

/**
 * 判断字符串是否为中文姓名（2-6 个中文字符）。
 */
fun String.isChineseName(): Boolean = matches(CHINESE_ID_NAME_REGEX)

/**
 * 判断字符串是否为中国大陆车牌号。
 */
fun String.isCarPlate(): Boolean = matches(CAR_PLATE_REGEX)

/**
 * 判断字符串是否为合法的 MAC 地址。
 */
fun String.isMacAddress(): Boolean = matches(MAC_ADDRESS_REGEX)

/**
 * 密码强度等级。
 */
enum class PasswordStrength {
    WEAK,
    MEDIUM,
    STRONG
}

/**
 * 获取密码强度等级。
 */
fun String.passwordStrength(): PasswordStrength {
    return when {
        isStrongPassword() -> PasswordStrength.STRONG
        isMediumPassword() -> PasswordStrength.MEDIUM
        else -> PasswordStrength.WEAK
    }
}
