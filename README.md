# aw-utils

[![](https://jitpack.io/v/answufeng/aw-utils.svg)](https://jitpack.io/#answufeng/aw-utils)

Android 工具扩展库，零业务逻辑，纯 Kotlin 扩展函数，覆盖字符串、日期、文件、网络、设备、编解码、应用、意图、屏幕、振动、富文本、正则、随机、进程、键盘等常用场景。

## 特性

- **纯扩展函数**：无侵入，按需引入，不污染现有代码
- **零业务逻辑**：通用工具，不绑定任何业务场景
- **最小依赖**：仅依赖 AndroidX 核心库，不引入第三方框架
- **线程安全**：日期格式化等内部使用 ThreadLocal + LRU 缓存
- **完整测试**：核心逻辑均有单元测试覆盖
- **API 稳定性**：实验性 API 使用 `@AwExperimentalApi` 标记，版本升级有保障

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
| 字符串 | StringExt | 手机号/邮箱/身份证/URL 校验、脱敏、MD5/SHA-256、truncate |
| 正则 | RegexExt | IP/中文/用户名/密码强度/车牌号/MAC 地址校验 |
| 日期 | DateExt | 格式化、解析、友好时间、日期比较 |
| 文件 | FileExt | 大小格式化、MD5/SHA-256、安全删除（含符号链接保护）、流写入 |
| 编解码 | EncodeExt | Base64、Hex 编解码（高性能查表实现） |
| 网络 | NetworkExt | 网络状态判断、Flow 状态监听、网络类型枚举 |
| 设备 | DeviceExt | 品牌、型号、系统版本、设备摘要（Context 扩展） |
| 应用 | AppExt | 安装检测、Debug 判断、启动应用、图标/名称获取、签名 SHA-1 |
| 意图 | IntentExt | 邮件/短信/相机/相册/地图/市场/设置页面跳转 |
| 屏幕 | ScreenExt | 平板判断、横竖屏、窗口亮度、屏幕常亮 |
| 上下文 | ContextExt | dp/sp 转换（Context 感知 + 无 Context 版本）、屏幕尺寸 |
| 系统 | SystemExt | 剪贴板、键盘（View/Activity/Fragment）、Toast、应用信息、权限检查 |
| 视图 | ViewExt | 防抖点击（debounceClick）、可见性控制、postDelay |
| Activity | ActivityExt | 泛型启动、类型安全参数获取、Fragment 支持 |
| 集合 | CollectionExt | ifNotEmpty、safeJoinToString |
| 富文本 | SpanExt | 颜色/加粗/斜体/下划线/删除线/点击/大小 Span，DSL 构建器 |
| 振动 | VibrateExt | 短振/自定义时长/模式振动/取消 |
| 随机 | RandomExt | 随机字符串/数字/颜色/列表元素 |
| 进程 | ProcessExt | 主线程判断、UI 线程执行 |
| 键盘 | KeyboardExt | 键盘可见性判断、键盘监听 |
| 日志 | AwLog | 轻量日志工具，全局开关、级别过滤（推荐迁移至 aw-log） |
| 偏好 | SpDelegate | SharedPreferences 属性委托（推荐迁移至 aw-store） |
| 注解 | Annotations | @AwExperimentalApi 实验性 API 标记 |

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

// 摘要（默认 UTF-8，可指定字符集）
"hello".md5()                       // "5d41402abc4b2a76b9719d911017c592"
"hello".sha256()                    // "2cf24dba5fb0a30e..."
"hello".md5(charset = Charsets.ISO_8859_1)  // 指定字符集

// 截断
"Hello World".truncate(5)           // "Hello…"
"Hello World".truncate(5, "...")    // "Hello..."
```

### RegexExt — 正则校验

```kotlin
"192.168.1.1".isIP()                // true
"你好世界".isChinese()               // true
"user123".isUsername()              // true (字母开头，6-20位)
"Abc@1234".isStrongPassword()       // true (8位+大小写+数字+特殊字符)
"abc123".isMediumPassword()         // true (6位+字母+数字)
"Abc@1234".passwordStrength()       // PasswordStrength.STRONG
"京A12345".isCarPlate()             // true
"00:1A:2B:3C:4D:5E".isMacAddress()  // true
```

### DateExt — 日期格式化与比较

```kotlin
// 格式化（内部使用 Locale.US + ThreadLocal LRU 缓存）
System.currentTimeMillis().formatDate()              // "2026-04-13 10:30:00"
System.currentTimeMillis().formatDate("yyyy/MM/dd")  // "2026/04/13"

// 解析
"2026-04-13".parseDate("yyyy-MM-dd")  // Date?

// 比较
timestamp.isToday()       // true
timestamp.isYesterday()   // false
time1.isSameDay(time2)    // true

// 友好时间（@AwExperimentalApi — 硬编码中文，未来可能国际化）
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
12345678L.toFriendlySize()  // "11.77 MB"

// 哈希
file.md5()             // "5d41402abc4b2a76b9719d911017c592"
file.sha256()          // "2cf24dba5fb0a30e..."

// 操作
file.ensureParentDir()             // 确保父目录存在
file.safeDeleteRecursively()       // 安全递归删除（含符号链接保护）
inputStream.writeToFile(file)      // 流写入文件
file.readTextOrNull()              // 安全读取文本，失败返回 null

// 属性
File("photo.JPG").extensionName    // "jpg"
```

### EncodeExt — 编解码

```kotlin
// Base64
"hello".encodeBase64()                // "aGVsbG8="
"aGVsbG8=".decodeBase64()             // ByteArray
"aGVsbG8=".decodeBase64ToString()     // "hello"

// Hex（高性能查表实现）
byteArrayOf(0x0A, 0xFF).toHexString()  // "0aff"
"0aff".hexToByteArray()                // [10, -1]
```

### NetworkExt — 网络状态

```kotlin
context.isNetworkAvailable()     // true
context.isWifiConnected()        // true
context.isMobileDataConnected()  // false
context.getNetworkType()         // NetworkType.WIFI

// Flow 监听（@AwExperimentalApi）
context.observeNetworkState().collect { available -> }
```

### DeviceExt — 设备信息

```kotlin
context.deviceBrand         // "Xiaomi"
context.deviceModel         // "MI 11"
context.deviceManufacturer  // "Xiaomi"
context.osVersion           // "14"
context.sdkVersion          // 34
context.deviceSummary()     // "Xiaomi MI 11 | Android 14 (SDK 34)" (@AwExperimentalApi)
```

### AppExt — 应用信息

```kotlin
context.isAppInstalled("com.tencent.mm")  // true
context.isAppDebug()                      // true
context.isAppForeground()                 // true
context.isSystemApp(packageName)          // false
context.launchApp("com.tencent.mm")       // 启动微信
context.getAppIcon("com.tencent.mm")      // Drawable?
context.getAppName("com.tencent.mm")      // "微信"
context.getAppSignatureSHA1()             // "a1b2c3..."
context.openAppDetailSettings()           // 打开应用详情设置
context.openAppDetailSettings("com.tencent.mm")  // 打开指定应用设置
```

### IntentExt — 系统意图

```kotlin
// 通讯
context.sendEmail("dev@example.com", subject = "反馈", text = "内容")
context.sendSMS("10086", message = "查询余额")

// 媒体
activity.openCamera(imageUri, REQUEST_CAMERA)
activity.pickImage(REQUEST_PICK_IMAGE)

// 位置
context.openMap(39.9042, 116.4074, label = "天安门")

// 市场
context.openAppMarket()                           // 当前应用
context.openAppMarket("com.tencent.mm")           // 指定应用

// 设置
context.openSettings()                             // 系统设置
context.openWifiSettings()                         // WiFi 设置
context.openLocationSettings()                     // 位置设置
context.openBluetoothSettings()                    // 蓝牙设置

// 安装
context.installApk(apkUri)
```

### ScreenExt — 屏幕信息

```kotlin
context.isTablet        // false
context.isLandscape     // false
context.isPortrait      // true

// 亮度
activity.getWindowBrightness()     // 128
activity.setWindowBrightness(200)  // 设置亮度
activity.resetWindowBrightness()   // 跟随系统

// 屏幕常亮
activity.isScreenKeepOn()          // false
activity.setScreenKeepOn(true)     // 保持常亮
```

### ContextExt — 尺寸转换与屏幕信息

```kotlin
// Context 感知转换（推荐，多窗口/折叠屏安全）
100.dpToPx(context)    // 300
14.spToPx(context)     // 42
48.pxToDp(context)     // 16.0
42.pxToSp(context)     // 14.0

// 无 Context 转换（@AwExperimentalApi — 使用 Resources.getSystem()）
100.dp                 // 300px
14.sp                  // 42px

// 屏幕
context.screenWidth          // 1080
context.screenHeight         // 2400
context.statusBarHeight      // 88
context.navigationBarHeight  // 96
```

### SystemExt — 系统服务

```kotlin
// 剪贴板
context.copyToClipboard("text")
context.getClipboardText()

// 键盘（支持 View / Activity / Fragment）
editText.showKeyboard()
view.hideKeyboard()
activity.hideKeyboard()
fragment.hideKeyboard()

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
context.hasPermission(Manifest.permission.CAMERA)
context.isPermissionGranted(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
```

### ViewExt — 视图扩展

```kotlin
view.debounceClick { handleClick() }
view.debounceClick(interval = 1000L) { handleClick() }

view.visible()
view.invisible()
view.gone()
view.setVisible(true)
view.setVisible(false, goneIfFalse = false)  // INVISIBLE
view.toggleVisibility()

setVisible(viewA, viewB, visible = false)

view.postDelay(300L) { doSomething() }
```

### ActivityExt — Activity 启动与参数

```kotlin
context.startActivity<DetailActivity>()
context.startActivity<DetailActivity>(bundleOf("id" to 123))
context.startActivity<DetailActivity> { putExtra("id", 123) }

val userId: String? = extraOrNull("user_id")
val count: Int? = extraOrNull("count")       // 不存在时返回 null（非 0）
val title: String? = argumentOrNull("title")
```

### CollectionExt — 集合扩展

```kotlin
list?.ifNotEmpty { items -> adapter.submitList(items.toList()) }
nullList.safeJoinToString()                    // ""
listOf("a", "b").safeJoinToString("|")         // "a|b"
```

### SpanExt — 富文本

```kotlin
// 单一样式
"Hello World".spanColor(Color.RED, 0, 5)
"Hello World".spanBold(0, 5)
"Hello World".spanItalic(0, 5)
"Hello World".spanUnderline(0, 5)
"Hello World".spanStrikethrough(0, 5)
"Hello World".spanSize(24.dp, 0, 5)
"Hello World".spanSuperscript(6, 8)
"Hello World".spanSubscript(6, 8)

// 可点击
textView.enableClickableSpan()
textView.text = "查看协议".spanClickable(0, 4) {
    openBrowser("https://example.com/policy")
}

// 组合样式（DSL 构建器）
textView.text = spannable {
    append("价格：".spanBold())
    append("¥99".spanColor(Color.RED))
    append(" ¥199".spanStrikethrough().spanColor(Color.GRAY))
}
```

### VibrateExt — 振动

```kotlin
context.vibrate()                           // 短振 50ms
context.vibrate(200L)                       // 自定义时长
context.vibrate(longArrayOf(0, 100, 50, 100))  // 模式振动
context.cancelVibrate()                     // 取消振动
```

### RandomExt — 随机

```kotlin
randomString(8)                    // "aB3kL9xZ"
randomNumericString(6)             // "384729"
randomLetterString(10)             // "kQmNxLpRst"
randomInt(1, 100)                  // 42
randomLong(0L, 999_999_999L)       // 123456789L
randomColor()                      // -14742431 (随机 ARGB)
randomColor(alpha = 128)           // 半透明随机色
listOf("🍎", "🍌", "🍊").randomElement()  // "🍌"
listOf(1, 2, 3, 4, 5).randomElements(2)  // [3, 1]
```

### ProcessExt — 进程与线程

```kotlin
if (isMainThread) { /* 主线程操作 */ }

runOnUiThread { updateUI() }               // 自动判断线程
runOnUiThreadDelayed(300L) { fadeIn() }    // 延迟主线程执行
```

### KeyboardExt — 键盘

```kotlin
// 判断键盘是否可见
if (isKeyboardVisible(rootView)) { hideKeyboard() }

// 监听键盘变化
val unsubscribe = observeKeyboardVisibility(rootView) { visible ->
    if (visible) adjustPadding() else resetPadding()
}
// 取消监听
unsubscribe()
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

## 实验性 API

部分 API 标记了 `@AwExperimentalApi`，调用时需显式 Opt-In：

```kotlin
@OptIn(AwExperimentalApi::class)
val px = 100.dp
val friendly = timestamp.toFriendlyTime()
```

当前标记为实验性的 API：
- `Number.dp` / `Number.dpF` / `Number.sp` / `Number.spF` — 使用 `Resources.getSystem()`
- `Long.toFriendlyTime()` — 硬编码中文，未来可能国际化
- `Context.observeNetworkState()` — Flow 实现细节可能调整
- `Context.deviceSummary()` — 输出格式可能调整

## 迁移指南（1.0.0 → 当前版本）

| 旧 API | 新 API | 说明 |
|--------|--------|------|
| `view.onClick { }` | `view.debounceClick { }` | 语义更清晰 |
| `view.postDelayed(300L) { }` | `view.postDelay(300L) { }` | 避免遮蔽 |
| `"text".ellipsize(5)` | `"text".truncate(5)` | 避免冲突 |
| `str.orDefault("x")` | `str ?: "x"` | Elvis 运算符 |
| `str.isNotNullOrBlank()` | `!str.isNullOrBlank()` | stdlib 原生 |
| `str.isNotNullOrEmpty()` | `!str.isNullOrEmpty()` | stdlib 原生 |
| `context.dp2px(16f)` | `16.dpToPx(context)` | Context 感知 |
| `context.sp2px(14f)` | `14.spToPx(context)` | Context 感知 |
| `context.px2dp(48)` | `48.pxToDp(context)` | 命名一致 |
| `context.px2sp(42)` | `42.pxToSp(context)` | 命名一致 |
| `deviceBrand`（顶层） | `context.deviceBrand` | Context 扩展 |
| `deviceSummary()`（顶层） | `context.deviceSummary()` | Context 扩展 |
| `"aGVsbG8=".decodeBase64String()` | `"aGVsbG8=".decodeBase64ToString()` | 命名惯例 |
| `currentTimeMillis()` | `System.currentTimeMillis()` | 无意义包装 |

> 所有旧 API 仍可用（标记 `@Deprecated(WARNING)`），不会破坏现有代码。

## 混淆

库已自带 consumer ProGuard 规则，无需额外配置。

## 许可证

Apache License 2.0，详见 [LICENSE](LICENSE)。
