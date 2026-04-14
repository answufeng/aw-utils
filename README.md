# aw-utils

[![](https://jitpack.io/v/answufeng/aw-utils.svg)](https://jitpack.io/#answufeng/aw-utils)

Android 工具扩展库，零业务逻辑，纯 Kotlin 扩展函数，覆盖字符串、日期、文件、网络、设备、编解码等常用场景。

## 特性

- **纯扩展函数**：无侵入，按需引入，不污染现有代码
- **零业务逻辑**：通用工具，不绑定任何业务场景
- **最小依赖**：仅依赖 AndroidX 核心库，不引入第三方框架
- **线程安全**：日期格式化等内部使用 ThreadLocal 缓存
- **完整测试**：核心逻辑均有单元测试覆盖

## 引入

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.answufeng:aw-utils:1.0.0")
}
```

## 功能概览

| 模块 | 文件 | 说明 |
|------|------|------|
| 字符串 | StringExt | 手机号/邮箱/身份证/URL 校验、脱敏、MD5/SHA-256 |
| 日期 | DateExt | 格式化、解析、友好时间、日期比较 |
| 文件 | FileExt | 大小格式化、MD5/SHA-256、安全删除、流写入 |
| 网络 | NetworkExt | 网络状态判断、Flow 状态监听、网络类型枚举 |
| 设备 | DeviceExt | 品牌、型号、系统版本、设备摘要 |
| 上下文 | ContextExt | dp/sp 转换、屏幕尺寸、状态栏/导航栏高度 |
| 编解码 | EncodeExt | Base64、Hex 编解码 |
| 系统 | SystemExt | 剪贴板、键盘、Toast、应用信息、权限检查、系统 Intent |
| 视图 | ViewExt | 防抖点击、可见性控制、切换可见性 |
| Activity | ActivityExt | 泛型启动、安全参数获取、Fragment 支持 |
| 集合 | CollectionExt | ifNotEmpty、safeJoinToString |
| 日志 | AwLog | 轻量日志工具，全局开关、级别过滤（推荐迁移至 aw-log） |
| 偏好 | SpDelegate | SharedPreferences 属性委托（推荐迁移至 aw-store） |

## API 文档

### StringExt — 字符串校验与脱敏

```kotlin
// 校验
"13812345678".isPhoneNumber()       // true
"test@mail.com".isEmail()           // true
"110101199001011234".isIdCard()     // true
"https://example.com".isUrl()       // true
"12345".isDigitsOnly()              // true

// 脱敏
"13812345678".maskPhone()           // "138****5678"
"110101199001011234".maskIdCard()   // "1101**********1234"
"hello@example.com".maskEmail()     // "h****@example.com"

// 摘要
"hello".md5()                       // "5d41402abc4b2a76b9719d911017c592"
"hello".sha256()                    // "2cf24dba5fb0a30e..."

// 工具
val name: String? = null
name.orDefault("匿名")               // "匿名"
name.isNotNullOrBlank()             // false
"Hello World".ellipsize(5)          // "Hello…"
```

### DateExt — 日期格式化与比较

```kotlin
// 格式化
System.currentTimeMillis().formatDate()              // "2026-04-13 10:30:00"
System.currentTimeMillis().formatDate("yyyy/MM/dd")  // "2026/04/13"

// 解析
"2026-04-13".parseDate("yyyy-MM-dd")  // Date?

// 比较
timestamp.isToday()       // true
timestamp.isYesterday()   // false
time1.isSameDay(time2)    // true

// 友好时间
(timestamp - 30_000).toFriendlyTime()   // "刚刚"
(timestamp - 5 * 60_000).toFriendlyTime()  // "5分钟前"
(timestamp - 3 * 3_600_000).toFriendlyTime()  // "3小时前"
(timestamp - 30 * 3_600_000).toFriendlyTime()  // "昨天 10:30"
```

### FileExt — 文件操作

```kotlin
// 大小
file.friendlySize()    // "1.5 MB"
dir.totalSize()        // 12345678L

// 哈希
file.md5()             // "5d41402abc4b2a76b9719d911017c592"
file.sha256()          // "2cf24dba5fb0a30e..."

// 操作
file.ensureParentDir()             // 确保父目录存在
file.safeDeleteRecursively()       // 安全递归删除
inputStream.writeToFile(file)      // 流写入文件
file.readTextOrNull()              // 安全读取文本，失败返回 null

// 属性
File("photo.JPG").extensionName    // "jpg"
```

### NetworkExt — 网络状态

```kotlin
// 判断
context.isNetworkAvailable()     // true
context.isWifiConnected()        // true
context.isMobileDataConnected()  // false

// 网络类型
context.getNetworkType()         // NetworkType.WIFI

// Flow 监听
context.observeNetworkState().collect { available ->
    // 响应式网络状态
}
```

### DeviceExt — 设备信息

```kotlin
deviceBrand         // "Xiaomi"
deviceModel         // "MI 11"
deviceManufacturer  // "Xiaomi"
osVersion           // "14"
sdkVersion          // 34
deviceSummary()     // "Xiaomi MI 11 | Android 14 (SDK 34)"
```

### ContextExt — 尺寸转换与屏幕信息

```kotlin
// 转换
context.dp2px(16f)    // 48
context.sp2px(14f)    // 42
100.dp                // 300px（不依赖 Context）
14.sp                 // 42px

// 屏幕
context.screenWidth          // 1080
context.screenHeight         // 2400
context.statusBarHeight      // 88
context.navigationBarHeight  // 96
```

### EncodeExt — 编解码

```kotlin
// Base64
"hello".encodeBase64()           // "aGVsbG8="
"aGVsbG8=".decodeBase64String()  // "hello"

// Hex
byteArrayOf(0x0A, 0xFF).toHexString()  // "0aff"
"0aff".hexToByteArray()                // [10, -1]
```

### SystemExt — 系统服务

```kotlin
// 剪贴板
context.copyToClipboard("text")
context.getClipboardText()       // "text"

// 键盘
editText.showKeyboard()
activity.hideKeyboard()

// Toast
context.toast("保存成功")
context.toastLong("操作完成")

// 系统 Intent
context.dial("10086")
context.openBrowser("https://github.com")
context.shareText("推荐一个好用的库")
context.openAppSettings()

// 应用信息
context.appVersionName()   // "1.0.0"
context.appVersionCode()   // 1L

// 权限
context.hasPermission(Manifest.permission.CAMERA)  // true
context.isPermissionGranted(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)  // false
```

### ViewExt — 视图扩展

```kotlin
// 防抖点击
view.onClick { handleClick() }
view.onClick(interval = 1000L) { handleClick() }

// 可见性
view.visible()
view.invisible()
view.gone()
view.setVisible(true)              // VISIBLE
view.setVisible(false)             // GONE
view.setVisible(false, goneIfFalse = false)  // INVISIBLE
view.toggleVisibility()            // 切换 VISIBLE ↔ GONE

// 批量
setVisible(viewA, viewB, visible = false)

// 延迟
view.postDelayed(300L) { doSomething() }
```

### ActivityExt — Activity 启动与参数

```kotlin
// 启动
context.startActivity<DetailActivity>()
context.startActivity<DetailActivity>(bundleOf("id" to 123))
context.startActivity<DetailActivity> {
    putExtra("id", 123)
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

// Fragment 中启动
startActivity<DetailActivity>()

// 结果
finishWithResult(Activity.RESULT_OK, intent)

// 参数
val userId: String? = extraOrNull("user_id")
val userId: String = extraOrDefault("user_id", "")
val title: String? = argumentOrNull("title")
```

### CollectionExt — 集合扩展

```kotlin
list?.ifNotEmpty { items -> adapter.submitList(items.toList()) }

val tags: List<String>? = null
tags.safeJoinToString()                    // ""
listOf("a", "b").safeJoinToString("|")     // "a|b"
```

### AwLog — 轻量日志（推荐迁移至 aw-log）

```kotlin
AwLog.init(isDebug = BuildConfig.DEBUG, prefix = "MyApp")

AwLog.d("Network", "请求成功")
AwLog.e("DB", "写入失败", exception)
AwLog.d("Network") { "响应数据: ${response.body}" }
```

### SpDelegate — SP 属性委托（推荐迁移至 aw-store）

```kotlin
object AppPrefs : SpDelegate("app_prefs") {
    var token by string("token", "")
    var userId by int("user_id", 0)
    var isFirstLaunch by boolean("first_launch", true)
}

AppPrefs.init(applicationContext)
AppPrefs.token = "abc123"
```

## 混淆

库已自带 consumer ProGuard 规则，无需额外配置。

## 许可证

Apache License 2.0，详见 [LICENSE](LICENSE)。
