package com.answufeng.utils

import android.os.Build

/**
 * 常见国内/国际厂商判断，基于 [Build.MANUFACTURER] 与 [Build.BRAND] 的**启发式**匹配（非官方 ROM API，仅作分支参考）。
 */
object Rom {

    fun isXiaomi(): Boolean {
        val m = Build.MANUFACTURER.lowercase()
        val b = Build.BRAND.lowercase()
        return m == "xiaomi" || m == "redmi" || m == "poco" || b == "redmi" || b == "poco" || b == "xiaomi"
    }

    fun isHuawei(): Boolean = Build.MANUFACTURER.equals("huawei", ignoreCase = true) ||
        Build.BRAND.equals("huawei", ignoreCase = true)

    fun isHonor(): Boolean = Build.MANUFACTURER.equals("honor", ignoreCase = true) ||
        Build.BRAND.equals("honor", ignoreCase = true)

    fun isOppo(): Boolean = Build.MANUFACTURER.equals("oppo", ignoreCase = true) ||
        Build.BRAND.equals("oppo", ignoreCase = true)

    fun isVivo(): Boolean = Build.MANUFACTURER.equals("vivo", ignoreCase = true) ||
        Build.BRAND.equals("vivo", ignoreCase = true)

    fun isOnePlus(): Boolean = Build.MANUFACTURER.equals("oneplus", ignoreCase = true) ||
        Build.BRAND.equals("oneplus", ignoreCase = true)

    fun isRealme(): Boolean = Build.MANUFACTURER.equals("realme", ignoreCase = true) ||
        Build.BRAND.equals("realme", ignoreCase = true)

    fun isMeizu(): Boolean = Build.MANUFACTURER.equals("meizu", ignoreCase = true) ||
        Build.BRAND.equals("meizu", ignoreCase = true)

    fun isSamsung(): Boolean = Build.MANUFACTURER.equals("samsung", ignoreCase = true) ||
        Build.BRAND.equals("samsung", ignoreCase = true)

    fun isLenovo(): Boolean = Build.MANUFACTURER.equals("lenovo", ignoreCase = true) ||
        Build.BRAND.equals("lenovo", ignoreCase = true)

    fun isGoogle(): Boolean = Build.MANUFACTURER.equals("google", ignoreCase = true) ||
        Build.BRAND.equals("google", ignoreCase = true)
}
