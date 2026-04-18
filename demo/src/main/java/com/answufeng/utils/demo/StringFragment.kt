package com.answufeng.utils.demo

import com.answufeng.utils.*

class StringFragment : BaseDemoFragment() {

    override fun setupDemo() {
        addTitle("📝 字符串校验")
        addLog("手机号: ${"13812345678".isPhoneNumber()}")
        addLog("邮箱: ${"test@example.com".isEmail()}")
        addLog("身份证: ${"110101199001011234".isIdCard()}")
        addLog("URL: ${"https://example.com".isUrl()}")
        addLog("银行卡: ${"6222021234567890".isBankCard()}")
        addLog("纯数字: ${"12345".isDigitsOnly()}")

        addTitle("🔒 脱敏")
        addLog("手机号: ${"13812345678".maskPhone()}")
        addLog("身份证: ${"110101199001011234".maskIdCard()}")
        addLog("邮箱: ${"hello@example.com".maskEmail()}")
        addLog("银行卡: ${"6222021234567890".maskBankCard()}")

        addTitle("🔑 哈希")
        addLog("MD5: ${"hello".md5()}")
        addLog("SHA256: ${"hello".sha256().take(16)}...")

        addTitle("✂️ 截断")
        addLog("\"Hello World\".truncate(5) = ${"Hello World".truncate(5)}")

        addTitle("🔍 正则校验")
        addLog("IP: ${"192.168.1.1".isIP()}")
        addLog("中文: ${"你好世界".isChinese()}")
        addLog("用户名: ${"user123".isUsername()}")
        addLog("强密码: ${"Abc@1234".isStrongPassword()}")
        addLog("车牌号: ${"京A12345".isCarPlate()}")
        addLog("MAC地址: ${"00:1A:2B:3C:4D:5E".isMacAddress()}")
    }
}
