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
    implementation("com.github.answufeng:aw-utils:1.0.0")
}
```

`implementation` 中的 **版本号与 Git / JitPack 的 tag 一致**（上例为 `1.0.0`）。

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
| 快速找到“某类扩展在哪” | [能力概览](#能力概览) |
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

本库定位为“**可维护的高频子集**”，不是 [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 的全量平替（避免单 AAR 无限膨胀）。

<details>
<summary><b>点击展开能力列表</b>（按文件名 / 主题快速定位）</summary>

| 主题 | 入口（常见文件/扩展名） | 说明 |
|------|--------------------------|------|
| 字符串 / 正则 | `StringExt` / `RegexExt` | 校验、脱敏、摘要、截断 |
| 日期 | `DateExt` | 格式化、解析、比较、加减、友好时间 |
| 文件 / Zip | `FileExt` / `ZipExt` | 大小/哈希、复制移动、解压（Zip Slip 校验） |
| 编解码 | `EncodeExt` | Base64、Hex |
| 网络 | `NetworkExt` | 连通性、网络类型、`Flow` 监听（部分为实验 API） |
| 设备 / 厂商 | `DeviceExt` / `Rom`（`RomExt`） | 型号/系统/厂商判断 |
| 存储路径 / 资源 | `StoragePathExt` / `AssetsRawExt` | cache/files/external、assets/raw |
| UI（View/Bar/通知/Span） | `ViewExt` / `BarExt` / `NotificationExt` / `SpanExt` | 防抖点击、系统栏、通知、富文本 DSL |
| 系统能力 | `SystemExt` / `KeyboardExt` / `ProcessExt` | 剪贴板、键盘、UI 线程工具等 |
| 其它常用 | `IntentExt` / `ActivityExt` / `ColorExt` / `UriExt` / `BitmapExt` | 常见跳转与小工具 |
| 兼容/内部 | `PackageManagerExt` 等 | 兼容性封装 |
| 迁移建议 | `AwLog` / `SpDelegate` | 建议迁移至 `aw-log` / `aw-store` |

</details>

---

## 常用示例

以下为常用写法速查；按主题折叠，便于“复制即用”。

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

