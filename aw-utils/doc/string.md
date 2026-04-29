# StringExt — 字符串校验 / 脱敏 / 哈希 / 截断

> 源码：[StringExt.kt](../src/main/java/com/answufeng/utils/StringExt.kt)

## 校验

| 扩展 | 说明 |
|------|------|
| `String.isPhoneNumber()` | 中国大陆手机号（1 开头，第二位 3-9，共 11 位） |
| `String.isChinesePhoneNumber()` | 严格号段校验的手机号（排除未分配号段） |
| `String.isEmail()` | 邮箱地址 |
| `String.isIdCard()` | 18 位身份证号（含校验码验证） |
| `String.isDigitsOnly()` | 仅数字 |
| `String.isUrl()` | http/https URL |
| `String.isBankCard()` | 银行卡号（16-19 位数字） |
| `String.isJson()` | JSON 格式（首字符为 `{` 或 `[`） |

```kotlin
"13812345678".isPhoneNumber()       // true
"13812345678".isChinesePhoneNumber() // true（号段有效）
"test@mail.com".isEmail()           // true
"110101199001011234".isIdCard()     // true（含加权校验）
"12345".isDigitsOnly()              // true
"https://example.com".isUrl()       // true
"6222021234567890".isBankCard()     // true
"{\"key\":1}".isJson()              // true
```

## 脱敏

| 扩展 | 示例输入 → 输出 |
|------|------------------|
| `String.maskPhone()` | `13812345678` → `138****5678` |
| `String.maskIdCard()` | `110101199001011234` → `1101**********1234` |
| `String.maskEmail()` | `hello@example.com` → `h****@example.com` |
| `String.maskBankCard()` | `6222021234567890` → `6222********7890` |
| `String.maskName()` | `张三` → `张*`，`欧阳修` → `欧**` |
| `String.mask(keepStart, keepEnd)` | 通用脱敏，保留首尾指定数量字符 |

```kotlin
"13812345678".maskPhone()
"110101199001011234".maskIdCard()
"hello@example.com".maskEmail()
"6222021234567890".maskBankCard()
"张三".maskName()                 // "张*"
"欧阳修".maskName()               // "欧**"
"13812345678".mask(3, 4)           // "138****5678"
```

> 长度不足时原样返回，不会截断。

## 哈希摘要

| 扩展 | 输出 |
|------|------|
| `String.md5()` | 32 位小写十六进制 |
| `String.sha1()` | 40 位小写十六进制 |
| `String.sha256()` | 64 位小写十六进制 |

```kotlin
"hello".md5()     // "5d41402abc4b2a76b9719d911017c592"
"hello".sha1()    // "aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d"
"hello".sha256()  // "2cf24dba5fb0a30e26e83b2ac5b9e29e..."
```

> 内部使用 ThreadLocal 缓存 `MessageDigest` 实例，线程安全且高性能。

## 截断

```kotlin
"Hello World".truncate(5)        // "Hello…"
"Hello World".truncate(5, "!")   // "Hello!"
```

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `String?.orDefault()` | Elvis 运算符 `str ?: default` |
| `String.ellipsize()` | `truncate()` |
| `String?.isNotNullOrBlank()` | `!isNullOrBlank()` |
| `String?.isNotNullOrEmpty()` | `!isNullOrEmpty()` |
