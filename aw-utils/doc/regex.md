# RegexExt — 正则校验 / 密码强度

> 源码：[RegexExt.kt](../src/main/java/com/answufeng/utils/RegexExt.kt)

## 校验

| 扩展 | 说明 |
|------|------|
| `String.isIP()` | IPv4 地址 |
| `String.isChinese()` | 仅中文字符 |
| `String.isUsername()` | 用户名（字母开头，6-20 位字母数字下划线） |
| `String.isStrongPassword()` | 强密码（≥8 位，含大小写字母 + 数字 + 特殊字符） |
| `String.isMediumPassword()` | 中等密码（≥6 位，含字母 + 数字） |
| `String.isChineseName()` | 中文姓名（2-6 个中文字符） |
| `String.isCarPlate()` | 中国大陆车牌号 |
| `String.isMacAddress()` | MAC 地址（冒号或短横线分隔） |

```kotlin
"192.168.1.1".isIP()              // true
"你好世界".isChinese()             // true
"user123".isUsername()            // true
"Abc@1234".isStrongPassword()    // true
"abc123".isMediumPassword()      // true
"张三".isChineseName()            // true
"京A12345".isCarPlate()          // true
"00:1A:2B:3C:4D:5E".isMacAddress() // true
```

## 密码强度等级

```kotlin
val strength = "Abc@1234".passwordStrength()
when (strength) {
    PasswordStrength.WEAK -> { /* 弱 */ }
    PasswordStrength.MEDIUM -> { /* 中 */ }
    PasswordStrength.STRONG -> { /* 强 */ }
}
```

| 等级 | 条件 |
|------|------|
| `STRONG` | ≥8 位，含大小写字母 + 数字 + 特殊字符 |
| `MEDIUM` | ≥6 位，含字母 + 数字 |
| `WEAK` | 不满足中等强度要求 |
