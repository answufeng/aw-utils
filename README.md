# aw-utils

[![JitPack](https://jitpack.io/v/answufeng/aw-utils.svg)](https://jitpack.io/#answufeng/aw-utils)

面向 **传统 View / XML** 的 Android 工具库：纯 Kotlin **扩展函数**、零业务逻辑，覆盖字符串、日期、文件、网络、设备、存储、通知、UI 等常用场景。

如果你只想最快接入并用上几个常用扩展，直接看下面的「5 分钟上手」即可；其它内容都可以后置按需查阅。

---

## 5 分钟上手（最小接入）

### 1) 添加依赖（JitPack）

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// app/build.gradle.kts（版本与 Git tag / JitPack 一致）
dependencies {
    implementation("com.github.answufeng:aw-utils:1.0.2")
}
```

`implementation` 中的 **版本号与 Git / JitPack 的 tag 一致**（上例为 `1.0.2`）。

### 2) 直接使用（Kotlin 扩展函数）

```kotlin
// 字符串
"13812345678".maskPhone()
"hello".md5()

// 网络
val ok = context.isNetworkAvailable()

// 文件
val size = File(cacheDir, "a.bin").friendlySize()

// View
button.debounceClick { /* ... */ }
```

### 3) 按需补齐权限（少数扩展需要）

涉及网络、通知、振动等能力时，对照下方 [权限](#权限) 在 Manifest 中声明即可。

---

## 目录（按常见需求跳转）

| 想做什么 | 跳转到 |
|----------|--------|
| 最短时间接入并开始用 | [5 分钟上手（最小接入）](#5-分钟上手最小接入) · [环境要求](#环境要求) |
| 哪些能力需要权限 | [权限](#权限) |
| 快速找到"某类扩展在哪" | [能力概览](#能力概览) |
| 查看某模块详细文档 | [模块文档](#模块文档) |
| 直接抄用常见代码片段 | [常用示例](#常用示例) |
| 看 Demo / 手测入口 | [演示应用](#演示应用) |
| 工程/发版/CI | [本仓库与工程检查](#本仓库与工程检查) |
| 混淆/许可证 | [混淆](#混淆) · [许可证](#许可证) |

---

## 环境要求

| 项目 | 最低版本 |
|------|----------|
| Kotlin | 2.0+ |
| Android minSdk | 24 |
| Demo compileSdk / targetSdk（验证用） | 35 |
| JDK | 17 |
| AGP | 8.x（建议） |

**依赖说明**：AndroidX（AppCompat、Core KTX、Fragment KTX、Annotation）+ `kotlinx-coroutines-core`（`api` 传递，供 `observeNetworkState()` 等使用）；不绑定 Gson / Retrofit 等第三方框架。

---

## 权限

| 权限 | 场景 | 需在 Manifest 声明 |
|------|------|---------------------|
| `ACCESS_NETWORK_STATE` | 网络状态、类型 | 是 |
| `VIBRATE` | 振动 | 是 |
| `POST_NOTIFICATIONS` | 通知（Android 13+） | 是 |
| `REQUEST_INSTALL_PACKAGES` | 安装 APK（Android 8+） | 是 |
| `CAMERA` | 拍照；少数机型手电筒 | 按需 |
| `MODIFY_AUDIO_SETTINGS` | `VolumeExt` 调系统音量 | 建议 |

---

## 能力概览

本库定位为"**可维护的高频子集**"，不是 [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 的全量平替（避免单 AAR 无限膨胀）。

| 主题 | 入口 | 说明 | 详细文档 |
|------|------|------|----------|
| 字符串 | `StringExt` | 校验、脱敏、摘要、截断 | [string.md](aw-utils/doc/string.md) |
| 正则 | `RegexExt` | IP/中文/密码/车牌/MAC | [regex.md](aw-utils/doc/regex.md) |
| 日期 | `DateExt` | 格式化、解析、比较、加减、友好时间 | [date.md](aw-utils/doc/date.md) |
| 文件 | `FileExt` | 大小/哈希、复制移动、安全删除 | [file.md](aw-utils/doc/file.md) |
| Zip | `ZipExt` | 压缩、解压（Zip Slip 校验） | [zip.md](aw-utils/doc/zip.md) |
| 编解码 | `EncodeExt` | Base64、Hex、URL、HTML | [encode.md](aw-utils/doc/encode.md) |
| 网络 | `NetworkExt` | 连通性、类型、Wi-Fi SSID、Flow 监听 | [network.md](aw-utils/doc/network.md) |
| 设备 | `DeviceExt` | 品牌/型号/系统版本/摘要 | [device.md](aw-utils/doc/device.md) |
| 厂商 | `RomExt`（`Rom`） | 国内/国际厂商启发式判断 | [rom.md](aw-utils/doc/rom.md) |
| 存储路径 | `StoragePathExt` | cache/files/external/obb、存储容量 | [storage-path.md](aw-utils/doc/storage-path.md) |
| Assets/Raw | `AssetsRawExt` | 读取/拷贝 assets 与 raw | [assets-raw.md](aw-utils/doc/assets-raw.md) |
| View | `ViewExt` | 防抖点击、触摸区域、可见性、Padding/Margin DSL | [view.md](aw-utils/doc/view.md) |
| 系统栏 | `BarExt` | 状态栏/导航栏颜色、样式、沉浸式、可见性 | [bar.md](aw-utils/doc/bar.md) |
| 通知 | `NotificationExt` | 渠道、简单通知、进度通知、大文本通知 | [notification.md](aw-utils/doc/notification.md) |
| 富文本 | `SpanExt` | Span DSL（颜色/粗体/斜体/下划线/点击/图片） | [span.md](aw-utils/doc/span.md) |
| 系统 | `SystemExt` | 剪贴板、Toast、拨号、分享、权限检查 | [system.md](aw-utils/doc/system.md) |
| 键盘 | `KeyboardExt` | 可见性检测与监听 | [keyboard.md](aw-utils/doc/keyboard.md) |
| 进程 | `ProcessExt` | 主线程判断、UI 线程执行 | [process.md](aw-utils/doc/process.md) |
| 意图 | `IntentExt` | 邮件/短信/地图/市场/系统设置/安装 APK | [intent.md](aw-utils/doc/intent.md) |
| Activity | `ActivityExt` | 泛型启动、Extra/Arguments、生命周期 | [activity.md](aw-utils/doc/activity.md) |
| 颜色 | `ColorExt` | 十六进制转换、透明度、混合 | [color.md](aw-utils/doc/color.md) |
| Uri | `UriExt` | 文件路径/文件名/MIME/本地判断 | [uri.md](aw-utils/doc/uri.md) |
| Bitmap | `BitmapExt` | 缩放/圆形/圆角/旋转/压缩/采样解码 | [bitmap.md](aw-utils/doc/bitmap.md) |
| 应用 | `AppExt` | 安装/Debug/启动/图标/签名/版本信息 | [app.md](aw-utils/doc/app.md) |
| 集合 | `CollectionExt` | 空判断、安全 joinToString、默认值 | [collection.md](aw-utils/doc/collection.md) |
| 尺寸转换 | `ContextExt` | dp/sp/px 转换、屏幕尺寸、深色模式、屏幕密度 | [context.md](aw-utils/doc/context.md) |
| EditText | `EditTextExt` | 文本监听、长度限制、小数过滤 | [edittext.md](aw-utils/doc/edittext.md) |
| 手电筒 | `FlashlightExt` | 闪光灯开关 | [flashlight.md](aw-utils/doc/flashlight.md) |
| ImageView | `ImageViewExt` | tint 着色 | [imageview.md](aw-utils/doc/imageview.md) |
| Meta-data | `MetaDataExt` | Manifest meta-data 读取 | [metadata.md](aw-utils/doc/metadata.md) |
| 随机 | `RandomExt` | 随机字符串/数/颜色/采样 | [random.md](aw-utils/doc/random.md) |
| 屏幕 | `ScreenExt` | 方向/亮度/常亮/平板判断/系统亮度 | [screen.md](aw-utils/doc/screen.md) |
| Service | `ServiceExt` | 启动/前台/停止服务 | [service.md](aw-utils/doc/service.md) |
| TextView | `TextViewExt` | Compound Drawable / Drawable Tint | [textview.md](aw-utils/doc/textview.md) |
| 振动 | `VibrateExt` | 短振/自定义/模式振动 | [vibrate.md](aw-utils/doc/vibrate.md) |
| 音量 | `VolumeExt` | 系统音量读取与设置 | [volume.md](aw-utils/doc/volume.md) |
| 清理 | `CleanExt` | 缓存目录清理 | [clean.md](aw-utils/doc/clean.md) |
| 资源关闭 | `CloseExt` | 静默关闭 Closeable | [close.md](aw-utils/doc/close.md) |
| 注解 | `Annotations` | `@AwExperimentalApi` | [annotations.md](aw-utils/doc/annotations.md) |
| 兼容 | `PackageManagerExt` | 内部兼容封装 | [packagemanager.md](aw-utils/doc/packagemanager.md) |
| ~~日志~~ | `AwLog` | ~~轻量日志~~ → 迁移至 aw-log | [aw-log.md](aw-utils/doc/aw-log.md) |
| ~~SP 委托~~ | `SpDelegate` | ~~SP 属性委托~~ → 迁移至 aw-store | [sp-delegate.md](aw-utils/doc/sp-delegate.md) |

---

## 模块文档

每个工具模块都有独立的详细文档，包含完整 API 参考、使用示例和注意事项：

📁 [aw-utils/doc/](aw-utils/doc/)

| 分类 | 文档 |
|------|------|
| **文本** | [字符串](aw-utils/doc/string.md) · [正则](aw-utils/doc/regex.md) · [编解码](aw-utils/doc/encode.md) |
| **时间** | [日期](aw-utils/doc/date.md) · [随机](aw-utils/doc/random.md) |
| **文件** | [文件](aw-utils/doc/file.md) · [Zip](aw-utils/doc/zip.md) · [清理](aw-utils/doc/clean.md) · [存储路径](aw-utils/doc/storage-path.md) · [Assets/Raw](aw-utils/doc/assets-raw.md) · [资源关闭](aw-utils/doc/close.md) |
| **设备** | [设备](aw-utils/doc/device.md) · [厂商](aw-utils/doc/rom.md) · [屏幕](aw-utils/doc/screen.md) · [尺寸转换](aw-utils/doc/context.md) |
| **网络** | [网络](aw-utils/doc/network.md) |
| **UI** | [View](aw-utils/doc/view.md) · [EditText](aw-utils/doc/edittext.md) · [TextView](aw-utils/doc/textview.md) · [ImageView](aw-utils/doc/imageview.md) · [Span](aw-utils/doc/span.md) · [颜色](aw-utils/doc/color.md) · [Bitmap](aw-utils/doc/bitmap.md) |
| **系统** | [系统](aw-utils/doc/system.md) · [键盘](aw-utils/doc/keyboard.md) · [进程](aw-utils/doc/process.md) · [振动](aw-utils/doc/vibrate.md) · [音量](aw-utils/doc/volume.md) · [手电筒](aw-utils/doc/flashlight.md) · [通知](aw-utils/doc/notification.md) · [系统栏](aw-utils/doc/bar.md) |
| **意图/跳转** | [Intent](aw-utils/doc/intent.md) · [Activity](aw-utils/doc/activity.md) · [Service](aw-utils/doc/service.md) |
| **应用** | [应用](aw-utils/doc/app.md) · [Meta-data](aw-utils/doc/metadata.md) · [Uri](aw-utils/doc/uri.md) · [集合](aw-utils/doc/collection.md) |
| **注解/内部** | [注解](aw-utils/doc/annotations.md) · [PackageManager](aw-utils/doc/packagemanager.md) |
| **已弃用** | [AwLog → aw-log](aw-utils/doc/aw-log.md) · [SpDelegate → aw-store](aw-utils/doc/sp-delegate.md) |

---

## 常用示例

以下为常用写法速查；按主题折叠，便于"复制即用"。

<details>
<summary><b>字符串 · 正则 · 日期 · 随机</b></summary>

```kotlin
"13812345678".isPhoneNumber()
"test@mail.com".isEmail()
"110101199001011234".isIdCard()
"13812345678".maskPhone()
"hello".md5()
"Hello World".truncate(5)

"192.168.1.1".isIP()
"Abc@1234".isStrongPassword()

System.currentTimeMillis().formatDate()
timestamp.addDays(3)

randomString(8)
listOf(1, 2, 3).randomElements(2)
```

</details>

<details>
<summary><b>文件 · 存储 · Zip · 编解码</b></summary>

```kotlin
file.friendlySize()
dir.totalSize()
file.copyToFileCatching(target)
file.decodeBitmapSampled(1080, 1080)

context.internalCacheDir
context.readAssetText("config.json")
context.copyAssetToFile("a.txt", File(filesDir, "b.txt"))
context.clearInternalCacheChildren()
sourceDir.zipDirectoryTo(File(cacheDir, "out.zip"))
zipFile.unzipToDirectory(targetDir)

"hello".encodeBase64()
byteArrayOf(0x0A).toHexString()
```

</details>

<details>
<summary><b>网络 · 设备 · Rom · 音量 · Service</b></summary>

```kotlin
context.isNetworkAvailable()
context.getNetworkType()

context.deviceBrand
Rom.isXiaomi()

context.getStreamVolume()
context.setStreamVolume(index = 5, flags = AudioManager.FLAG_SHOW_UI)

context.startServiceCompat<MyService> { }
context.startForegroundServiceCompat<MyFgService>()
```

</details>

<details>
<summary><b>应用 · 意图 · 屏幕 · 系统栏 · 通知 · Meta · 手电筒 · 振动</b></summary>

```kotlin
context.launchApp("com.tencent.mm")
context.openWirelessSettings()
context.installApk(uri)

activity.setWindowBrightness(200)
activity.setStatusBarColorAndStyle(Color.WHITE, darkStatusBarIcons = true)

context.showSimpleNotification(
    channelId = "ch",
    notificationId = 1,
    title = "Hi",
    text = "…",
    smallIcon = android.R.drawable.ic_dialog_info,
)
context.getApplicationMetaDataString("KEY")

context.setTorchEnabled(true)
context.vibrate(50L)
```

</details>

<details>
<summary><b>View · Color · Uri · Bitmap · Text · EditText · Activity · Span · 键盘 · 进程</b></summary>

```kotlin
view.debounceClick { }
view.expandTouchArea(extraDp = 16, delegateParent = parentLayout)

"#FF0000".toColorInt()
uri.toFilePath(context)

drawable.toBitmap()
bitmap.toRounded(16f)

editText.onTextChanged { }
context.startActivity<DetailActivity>(bundleOf("id" to 1))
extraOrNull<String>("key")

textView.text = spannable { append("A".spanBold()) }

rootView.observeKeyboardVisibility { }
runOnUiThread { }
```

</details>

更多参数与边界行为以 **源码 KDoc** 为准。

---

## 演示应用

- 模块：`demo/`
- 功能与入口对照：[demo/DEMO_MATRIX.md](demo/DEMO_MATRIX.md)

---

## 本仓库与工程检查

| 项 | 说明 |
|----|------|
| 本地建议命令 | `./gradlew :aw-utils:assembleRelease :aw-utils:ktlintCheck :aw-utils:lintRelease :demo:assembleRelease`（需 **JDK 17**） |
| CI | [`.github/workflows/ci.yml`](.github/workflows/ci.yml)：assemble、ktlint、R8 冒烟、Lint |
| 贡献 | [CONTRIBUTING.md](CONTRIBUTING.md) |
| 版本号 | 与 [gradle.properties](gradle.properties) 中 `VERSION_NAME`、Git **tag**、JitPack 保持一致 |
| 迁移建议 | `AwLog` → [aw-log](https://github.com/answufeng/aw-log)；`SpDelegate` → [aw-store](https://github.com/answufeng/aw-store) |

---

## 混淆

库通过 `consumer-rules.pro` 提供 **Consumer ProGuard** 规则；宿主一般无需再抄一份。

---

## 许可证

Apache License 2.0，见 [LICENSE](LICENSE)。
