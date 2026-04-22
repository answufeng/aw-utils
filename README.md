# aw-utils

[![](https://jitpack.io/v/answufeng/aw-utils.svg)](https://jitpack.io/#answufeng/aw-utils)

Android 工具扩展库，零业务逻辑，纯 Kotlin 扩展函数，覆盖字符串、日期、文件、网络、设备、编解码、应用、意图、屏幕、振动、富文本、正则、随机、进程、键盘、颜色、图片、Uri 等常用场景。

## 文档导读

1. [工程品质与发版检查](#工程品质与发版检查) → [弃用 API 迁移](#弃用-api-迁移达到生产级一致性与可维护性) → [功能概览](#功能概览)  
2. 演示：[demo/DEMO_MATRIX.md](demo/DEMO_MATRIX.md)（含 **推荐手测**）；菜单 **「演示清单」** 列出 Tab

## 特性

- **纯扩展函数**：无侵入，按需引入，不污染现有代码
- **零业务逻辑**：通用工具，不绑定任何业务场景
- **最小依赖**：仅依赖 AndroidX 核心库，不引入第三方框架
- **线程安全**：日期格式化等内部使用 ThreadLocal + LRU 缓存，网络监听使用 ConcurrentHashMap
- **验证方式**：**demo** 与集成侧手测为主
- **API 稳定性**：实验性 API 使用 `@AwExperimentalApi` 标记，版本升级有保障

## 兼容性

- **minSdk**: 24 (Android 7.0)
- **验证环境**: 本仓库 `demo` 模块使用 **compileSdk 35** / **targetSdk 35**（与库 `minSdk 24` 兼容说明一致）
- **Kotlin**: 2.0+

## 构建环境

- **JDK 17+**：Android Gradle Plugin 8.x 要求；若本机为 JDK 11，会出现 *Android Gradle plugin requires Java 17*。请在 IDE 的 Gradle JDK、`JAVA_HOME` 或 `~/.gradle/gradle.properties` 中指定 JDK 17（勿在仓库内提交本机路径，见 [gradle.properties](gradle.properties) 注释）。

## 工程品质与发版检查

- **CI**：[`.github/workflows/ci.yml`](.github/workflows/ci.yml) — `assembleRelease`、`ktlintCheck`、`lintRelease`、`:demo:assembleRelease`。
- **本地建议**：`./gradlew :aw-utils:assembleRelease :aw-utils:ktlintCheck :aw-utils:lintRelease :demo:assembleRelease`（需 JDK 17+）
- **演示**：[demo/DEMO_MATRIX.md](demo/DEMO_MATRIX.md)；demo 菜单 **「演示清单」** 列出各 Tab。
- **上线前**：凡使用网络/振动/安装等 API，按 README「权限说明」补齐 Manifest；新工程日志与存储请走 **aw-log** / **aw-store**，勿沿用已弃用 API。

### 弃用 API 迁移（达到生产级一致性与可维护性）

| 本库类型 | 替代方案 | 说明 |
|---------|---------|------|
| `AwLog` | [aw-log](https://github.com/answufeng/aw-log) `AwLogger` + Timber | 文件、脱敏、崩溃、拦截器链 |
| `SpDelegate` | [aw-store](https://github.com/answufeng/aw-store) `MmkvDelegate` + `AwStore.init` | MMKV 性能与多进程 |

IDE 对已标记 `@Deprecated` 的符号会提示 **ReplaceWith**；新工程请直接依赖 aw-log / aw-store，避免双轨。

## 引入

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// app/build.gradle.kts（版本以 JitPack / Git 标签为准，与 gradle.properties 的 VERSION_NAME 一致）
dependencies {
    implementation("com.github.answufeng:aw-utils:1.2.0")
}
```

## 权限说明

部分 API 需要声明对应权限：

| 权限 | 使用场景 | 需要手动声明 |
|------|----------|-------------|
| `ACCESS_NETWORK_STATE` | 网络状态判断、网络类型获取 | 是 |
| `VIBRATE` | 振动相关功能 | 是 |
| `REQUEST_INSTALL_PACKAGES` | 安装 APK（Android 8+） | 是 |
| `CAMERA` | 相机拍照 | 是 |

## 功能概览

| 模块 | 文件 | 说明 |
|------|------|------|
| 字符串 | StringExt | 手机号/严格号段/邮箱/身份证（含末位校验）/URL/银行卡校验、脱敏、`mask`、MD5/SHA-256、truncate |
| 正则 | RegexExt | IP/中文/用户名/密码强度/车牌号/MAC 地址校验 |
| 日期 | DateExt | 格式化、解析、友好时间、日期比较、加减（日/时/分/秒/周 等，含 DST 注意说明） |
| 文件 | FileExt | 大小格式化、MD5/SHA-256、按扩展名判断图片/音/视频、安全删除、复制、移动 |
| 编解码 | EncodeExt | Base64、Hex 编解码（高性能查表实现） |
| 网络 | NetworkExt | 网络状态判断、Flow 状态监听、网络类型枚举 |
| 设备 | DeviceExt | 品牌、型号、系统版本、设备摘要（Context 扩展） |
| 应用 | AppExt | 安装检测、Debug 判断、启动应用、图标/名称获取、签名 SHA-1（内部统一 `getPackageInfo` 新 API 兼容） |
| 意图 | IntentExt | 邮件/短信/地图等（`safeStartActivity`）、打开/分享文件、相机/相册/市场/设置/安装 APK |
| 屏幕 | ScreenExt | 平板判断、横竖屏、窗口亮度、屏幕常亮 |
| 上下文 | ContextExt | dp/sp 转换、屏幕尺寸、深色模式、外部存储是否可用/可写 |
| 系统 | SystemExt | 剪贴板、键盘（View/Activity/Fragment）、Toast、应用信息、权限检查 |
| 视图 | ViewExt | 防抖点击、可见性、padding/margin DSL、px/dp 宽高、单方向 padding |
| Activity | ActivityExt | 泛型启动、类型安全参数、Fragment 支持、生命周期 `isAlive` / `isDestroyedCompat` |
| 集合 | CollectionExt | ifNotEmpty、ifEmpty、safeJoinToString |
| 富文本 | SpanExt | 颜色/加粗/下划线/图片 Span 等，DSL 与 `spannable` 可继续追加 |
| 振动 | VibrateExt | 短振/自定义时长/模式振动/取消 |
| 随机 | RandomExt | 随机字符串/数字/颜色/列表元素（Fisher–Yates 多样本） |
| 进程 | ProcessExt | 主线程判断、UI 线程执行/延迟、延迟任务返回 `Runnable` 可取消 |
| 键盘 | KeyboardExt | 可见性/监听：View、Activity、Fragment |
| 颜色 | ColorExt | 颜色 Int ↔ Hex 转换、透明度调整、颜色混合 |
| Uri | UriExt | content:// 转 file path、获取文件名、获取 MIME 类型 |
| Bitmap | BitmapExt | 缩放、圆角、圆形、旋转、压缩保存、Drawable 互转 |
| TextView | TextViewExt | drawable 设置（含 tint）、drawable 资源 ID 版本 |
| EditText | EditTextExt | `onTextChanged` 返回 `TextWatcher` 可移除、最大长度、小数过滤、键盘动作 |
| ImageView | ImageViewExt | tint 设置/清除、图片+tint 组合设置 |
| 日志 | AwLog | 轻量日志工具，全局开关、级别过滤（推荐迁移至 aw-log） |
| 偏好 | SpDelegate | SharedPreferences 属性委托、`edit{}` 批量编辑（推荐迁移至 aw-store） |
| 注解 | Annotations | @AwExperimentalApi 实验性 API 标记 |
| （内部） | PackageManagerExt | 非公开：PackageManager 新旧 `getPackageInfo` 兼容，供本库其它扩展复用 |

## 演示应用

（发版命令见上文 **「工程品质与发版检查」**。）`demo` 为多 Tab Fragment；**「更多」** Tab 集中演示主线程可取消任务、`openMap` / `isDarkMode`、外置存储状态、`SpDelegate.edit`、`TextWatcher` 移除及 `Bitmap.rotate` 等；各 Tab 与 1.2 API 的对应见 [demo/DEMO_MATRIX.md](demo/DEMO_MATRIX.md)。工具栏 **「演示清单」** 列出各 Tab 名称。弃用 API 见「弃用 API 迁移」。

## API 文档

### StringExt — 字符串校验与脱敏

```kotlin
// 校验
"13812345678".isPhoneNumber()       // true
"test@mail.com".isEmail()           // true
"110101199001011234".isIdCard()     // true
"https://example.com".isUrl()       // true
"12345".isDigitsOnly()              // true
"6222021234567890".isBankCard()     // true
"13800001111".isChinesePhoneNumber() // 严格号段

// 脱敏
"12345678".mask(2, 2)               // 通用：保留首尾
"13812345678".maskPhone()           // "138****5678"
"110101199001011234".maskIdCard()   // "1101**********1234"
"hello@example.com".maskEmail()     // "h****@example.com"
"6222021234567890".maskBankCard()   // "6222********7890"

// 摘要（默认 UTF-8，可指定字符集）
"hello".md5()                       // "5d41402abc4b2a76b9719d911017c592"
"hello".sha256()                    // "2cf24dba5fb0a30e..."

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

// 日期计算
timestamp.startOfDay()    // 当天 00:00:00.000
timestamp.endOfDay()      // 当天 23:59:59.999
timestamp.addDays(3)      // 加 3 天
timestamp.addHours(2)     // 加 2 小时
timestamp.addMinutes(30)  // 加 30 分钟
timestamp.addSeconds(45)
timestamp.addWeeks(1)     // 夏令时边界见 KDoc 说明

// 友好时间（@AwExperimentalApi — 硬编码中文，未来可能国际化）
(timestamp - 30_000).toFriendlyTime()   // "刚刚"
(timestamp - 5 * 60_000).toFriendlyTime()  // "5分钟前"
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
file.copyTo(target)                // 复制文件
file.moveTo(target)                // 移动文件（先尝试 renameTo，失败则复制+删除）
inputStream.writeToFile(file)      // 流写入文件
file.readTextOrNull()              // 安全读取文本，失败返回 null

// 属性
File("photo.JPG").extensionName    // "jpg"
file.isImage()                     // 按扩展名
file.isVideo()
file.isAudio()
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
context.isNetworkType(NetworkType.WIFI)  // true

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
context.isAppForeground()                 // true（注意：Android 10+ 可能不可靠）
context.isSystemApp(packageName)          // false
context.launchApp("com.tencent.mm")       // 启动微信
context.getAppIcon("com.tencent.mm")      // Drawable?
context.getAppName("com.tencent.mm")      // "微信"
context.getAppSignatureSHA1()             // "a1b2c3..."
context.openAppDetailSettings()           // 打开应用详情设置
```

### IntentExt — 系统意图

```kotlin
// 通讯（返回是否成功调起；内部使用 safeStartActivity）
context.sendEmail("dev@example.com", subject = "反馈", text = "内容")  // true / false
context.sendSMS("10086", message = "查询余额")

// 安全启动
context.safeStartActivity(Intent(...))

// 媒体（已废弃，推荐使用 Activity Result API）
activity.openCamera(imageUri, REQUEST_CAMERA)
activity.pickImage(REQUEST_PICK_IMAGE)

// 位置
context.openMap(39.9042, 116.4074, label = "天安门")  // Boolean

// 用第三方应用打开 / 分享文件（content Uri + MIME）
context.openFile(uri, "application/pdf")
context.shareFile(uri, "image/png", title = "分享")

// 安装（注意：Android 7+ 需要 FileProvider，Android 8+ 需要 REQUEST_INSTALL_PACKAGES 权限）
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

// 屏幕（注意：多窗口/分屏模式下可能不准确）
context.screenWidth          // 1080
context.screenHeight         // 2400
context.statusBarHeight      // 88
context.navigationBarHeight  // 96

// 系统配置与存储（Environment 状态）
context.isDarkMode
context.isExternalStorageAvailable
context.isExternalStorageWritable
```

### SystemExt — 系统服务

```kotlin
// 剪贴板（注意：Android 10+ 后台无法读取，Android 13+ 系统自动提示）
context.copyToClipboard("text")
context.getClipboardText()

// 键盘（View / Activity / Fragment）
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
// 防抖点击（使用 SystemClock.elapsedRealtime()，不受系统时间调整影响）
view.debounceClick { handleClick() }
view.debounceClick(interval = 1000L) { handleClick() }

// 可见性
view.visible()
view.invisible()
view.gone()
view.setVisible(true)
view.setVisible(false, goneIfFalse = false)  // INVISIBLE
view.toggleVisibility()
setVisible(viewA, viewB, visible = false)
listOf(viewA, viewB).setVisible(visible = true)

// Padding DSL
view.updatePadding {
    left = 16.dpToPx(context)
    right = 16.dpToPx(context)
}

// Margin DSL（需要 MarginLayoutParams）
view.updateMargin {
    left = 16.dpToPx(context)
}

// 宽高（px 或 layoutParams 为空时见 KDoc/异常行为）
view.setWidth(500)
view.setHeight(300)
view.setWidthDp(120, context)
view.setHeightDp(48, context)
view.setPaddingStart(8.dpToPx(context))

view.postDelay(300L) { doSomething() }
```

### ColorExt — 颜色操作

```kotlin
// Int ↔ Hex 转换
(-14742431).toHexColor()          // "#FF1F71B3"
(-14742431).toHexColorNoAlpha()   // "#1F71B3"
"#1F71B3".toColorInt()            // -14742431

// 支持多种格式
"#RGB".toColorInt()
"#ARGB".toColorInt()
"#RRGGBB".toColorInt()
"#AARRGGBB".toColorInt()

// 透明度
color.withAlpha(128)               // 设置半透明

// 混合
Color.RED.blend(Color.BLUE, 0.5f)  // 红蓝各半混合
```

### UriExt — Uri 操作

```kotlin
// content:// 转 file path（云存储 Uri 可能返回 null）
uri.toFilePath(context)

// 获取文件名
uri.getFileName(context)

// 获取 MIME 类型
uri.getMimeType(context)            // "image/jpeg"

// 判断是否本地文件
uri.isLocalFile()
```

### BitmapExt — 图片操作

```kotlin
// Drawable → Bitmap
drawable.toBitmap()

// 缩放
bitmap.scale(newWidth, newHeight)
bitmap.scaleMaxSize(1024)           // 最大边不超过 1024

// 圆形/圆角
bitmap.toCircle()
bitmap.toRounded(16f)

bitmap.rotate(90f)

// 压缩保存
bitmap.compressTo(file, format = Bitmap.CompressFormat.JPEG, quality = 80)
```

### TextViewExt — TextView 扩展

```kotlin
// 设置 drawable
textView.setDrawableStart(drawable)
textView.setDrawableEnd(drawable, boundsWidth = 24, boundsHeight = 24)
textView.setDrawableTopRes(R.drawable.icon)
textView.setDrawableBottomRes(R.drawable.icon)

// Drawable tint
textView.setDrawableTint(Color.RED)

// 清除
textView.clearDrawables()
```

### EditTextExt — EditText 扩展

```kotlin
// 文本变化监听
editText.onTextChanged { text ->
    viewModel.onInputChanged(text)
}

// 输入限制
editText.setMaxLength(50)
editText.addDecimalFilter(2)        // 限制 2 位小数

// 键盘动作
editText.setOnEditorAction(EditorInfo.IME_ACTION_SEARCH) {
    performSearch()
}

// 清除焦点 + 隐藏键盘
editText.clearFocusAndHideKeyboard()
```

### ImageViewExt — ImageView 扩展

```kotlin
// Tint
imageView.setTint(Color.RED)
imageView.clearTint()
imageView.setImageWithTint(R.drawable.icon, Color.RED)
```

### ActivityExt — Activity 启动与参数

```kotlin
context.startActivity<DetailActivity>()
context.startActivity<DetailActivity>(bundleOf("id" to 123))
context.startActivity<DetailActivity> { putExtra("id", 123) }

val userId: String? = extraOrNull("user_id")
val count: Int? = extraOrNull("count")
val title: String? = argumentOrNull("title")

if (activity.isAlive) { /* 更新 UI */ }
// activity.isDestroyedCompat 等价 isDestroyed，命名强调异步前检查
```

### CollectionExt — 集合扩展

```kotlin
list?.ifNotEmpty { items -> adapter.submitList(items.toList()) }
list?.ifEmpty { showEmptyView() }
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
"Hello World".spanSize(24.dpToPx(context), 0, 5)  // 像素单位
"Hello World".spanSizeDp(24, context, 0, 5)        // dp 单位（便捷方法）
"Hello World".spanSuperscript(6, 8)
"Hello World".spanSubscript(6, 8)
" ".spanImage(drawable)  // ImageSpan；区间需覆盖用于占位的字符

// 可点击
textView.enableClickableSpan()
textView.text = "查看协议".spanClickable(0, 4) {
    openBrowser("https://example.com/policy")
}

// 组合样式（`spannable { }` 返回 `SpannableStringBuilder`，可再 append）
textView.text = spannable {
    append("价格：".spanBold())
    append("¥99".spanColor(Color.RED))
    append(" ¥199".spanStrikethrough().spanColor(Color.GRAY))
}
```

### VibrateExt — 振动

```kotlin
// 需要 android.permission.VIBRATE 权限
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
val task = runOnUiThreadDelayed(300L) { fadeIn() }  // 返回 Runnable
removeUiThreadCallback(task)               // 可取消
```

### KeyboardExt — 键盘

```kotlin
// View
if (rootView.isKeyboardVisible()) { /* ... */ }
rootView.observeKeyboardVisibility { visible -> }

// Activity
if (activity.isKeyboardVisible()) { /* ... */ }
activity.observeKeyboardVisibility { visible -> }

// Fragment（基于 `view`；`observeKeyboardVisibility` 在 `view == null` 时返回无操作取消器）
if (fragment.isKeyboardVisible()) { /* ... */ }
fragment.observeKeyboardVisibility { visible -> }
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

AppPrefs.edit {
    putString("token", "new")
    remove("temp")
}
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

## 迁移指南（1.0.0 → 1.1.0）

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
| `isKeyboardVisible(rootView)` | `rootView.isKeyboardVisible()` | View 扩展 |
| `observeKeyboardVisibility(rootView)` | `rootView.observeKeyboardVisibility()` | View 扩展 |
| `activity.openCamera(uri, requestCode)` | Activity Result API | 废弃 API |

> 所有旧 API 仍可用（标记 `@Deprecated(WARNING)`），不会破坏现有代码。

## 混淆

库已自带 consumer ProGuard 规则，无需额外配置。

## 许可证

Apache License 2.0，详见 [LICENSE](LICENSE)。

# Last updated: 2026年4月22日
