# aw-utils

[![JitPack](https://jitpack.io/v/answufeng/aw-utils.svg)](https://jitpack.io/#answufeng/aw-utils)

面向 **传统 View / XML** 的 Android 工具库：纯 Kotlin **扩展函数**、零业务逻辑，覆盖字符串、日期、文件、网络、设备、存储、通知、UI 等常用场景。

> **生态**：[answufeng](https://github.com/answufeng) 组织内另有 `aw-*` 基础库（架构、存储、网络、图片、日志等），与本库分工协作；工程基线多为 **minSdk 24**、**JDK 17**。

---

## 目录

|  |  |
|--|--|
| [这是什么 & 与 UtilCode 的差异](#这是什么--与-utilcode-的差异) | [快速开始](#快速开始) |
| [引入依赖](#引入依赖) | [环境要求](#环境要求) |
| [权限](#权限) | [模块一览](#模块一览) |
| [API 代码示例](#api-代码示例) | [演示应用](#演示应用) |
| [工程与发版](#工程与发版) | [实验性 API](#实验性-api) |
| [旧版迁移（1.0 → 1.1）](#旧版迁移10--11) | [混淆](#混淆) · [许可证](#许可证) |

---

## 这是什么 & 与 UtilCode 的差异

本库是 **精选高频能力**，不是 [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode) 的全量平替（避免单 AAR 无限膨胀）。

| 维度 | AndroidUtilCode | aw-utils |
|------|-----------------|----------|
| 体量 | 功能面极宽 | 可维护子集 |
| 风格 | Java `*Utils` | Kotlin **扩展** |
| 依赖 | 能力大量内置 | AndroidX + **Kotlin 协程**（`Flow` 等） |

---

## 快速开始

1. 在 `settings.gradle.kts` 中加入 **JitPack** 仓库（见 [引入依赖](#引入依赖)）。
2. 依赖坐标：`com.github.answufeng:aw-utils:1.0.0`（或与 Git **Tag** 一致）。
3. 在 `Context` / `String` / `File` 等接收者上直接调用扩展，例如：`context.isNetworkAvailable()`、`"138****".maskPhone()`、`file.decodeBitmapSampled(1080, 1080)`。
4. 涉及网络、通知、振动等能力时，对照下方 [权限](#权限) 在 Manifest 中声明。

---

## 引入依赖

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}

// app/build.gradle.kts（版本与 Git Tag / JitPack 一致）
dependencies {
    implementation("com.github.answufeng:aw-utils:1.0.0")
}
```

---

## 环境要求

| 项 | 说明 |
|----|------|
| **minSdk** | 24（Android 7.0） |
| **Kotlin** | 2.0+ |
| **JDK** | **17+**（AGP 8.x 必需；JDK 11 会报错） |
| **验证** | 本仓库 `demo` 使用 compileSdk / targetSdk **35** |

**依赖说明**：AndroidX（AppCompat、Core KTX、Fragment KTX、Annotation）+ `kotlinx-coroutines-core`（`api` 传递，供 `observeNetworkState()` 等使用）；**无** Gson / Retrofit 等第三方框架。

**本地构建示例**（需 JDK 17）：

```bash
./gradlew :aw-utils:assembleRelease :aw-utils:ktlintCheck :aw-utils:lintRelease :demo:assembleRelease
```

---

## 权限

| 权限 | 场景 | 需在 Manifest 声明 |
|------|------|---------------------|
| `ACCESS_NETWORK_STATE` | 网络状态、类型 | 是 |
| `VIBRATE` | 振动 | 是 |
| `REQUEST_INSTALL_PACKAGES` | 安装 APK（8+） | 是 |
| `CAMERA` | 拍照；少数机型手电筒 | 按需 |
| `POST_NOTIFICATIONS` | 通知（13+） | 是 |
| `MODIFY_AUDIO_SETTINGS` | `VolumeExt` 调系统音量 | 建议 |

---

## 模块一览

<details>
<summary><b>点击展开完整表格</b>（约 40 行）</summary>

| 模块 | 源文件 | 说明 |
|------|--------|------|
| 字符串 | StringExt | 校验、脱敏、`mask`、摘要、截断 |
| 正则 | RegexExt | IP、中文、密码强度、车牌等 |
| 日期 | DateExt | 格式化、解析、比较、加减、友好时间 |
| 文件 | FileExt | 大小/哈希、安全删除、复制移动、`Result`、防环 `totalSize` |
| 编解码 | EncodeExt | Base64、Hex |
| 网络 | NetworkExt | 连通性、类型、`Flow` 监听 |
| 设备 | DeviceExt | 品牌、型号、系统版本、摘要 |
| 厂商 | Rom（RomExt） | 常见厂商启发式判断 |
| 音量 | VolumeExt | 媒体流音量读写 |
| 服务 | ServiceExt | 启动 / 前台启动 / 停止 |
| 应用 | AppExt | 安装、Debug、启动、签名等 |
| 意图 | IntentExt | 邮件、地图、安装、系统设置等 |
| 屏幕 | ScreenExt | 平板、方向、窗口亮度、常亮 |
| 上下文 | ContextExt | dp/sp、屏幕、深色、外置存储 |
| 存储路径 | StoragePathExt | cache、files、external、OBB 等 |
| 资源 | AssetsRawExt | assets / raw、拷贝到文件 |
| 清理 | CleanExt | 清理 cache / files / externalCache 子项 |
| 压缩 | ZipExt | 目录 zip、解压（Zip Slip 校验） |
| 系统栏 | BarExt | 状态栏、导航栏颜色与对比度 |
| 通知 | NotificationExt | 渠道、简单通知 |
| Meta-data | MetaDataExt | Manifest `meta-data` 读取 |
| 系统 | SystemExt | 剪贴板、键盘、Toast、权限等 |
| 视图 | ViewExt | 防抖、扩大触控、可见性、DSL |
| Activity | ActivityExt | 泛型启动、参数、生命周期 |
| 集合 | CollectionExt | 空安全拼接等 |
| 富文本 | SpanExt | Span DSL |
| 振动 | VibrateExt | 振动模式 |
| 手电筒 | FlashlightExt | `CameraManager` torch |
| 随机 | RandomExt | 随机串、颜色、抽样 |
| 进程 | ProcessExt | 主线程、`runOnUiThread` |
| 键盘 | KeyboardExt | 可见性监听 |
| 颜色 | ColorExt | Hex、混合 |
| Uri | UriExt | path、MIME |
| Bitmap | BitmapExt | 缩放、圆角、采样解码等 |
| TextView / EditText / ImageView | 对应 Ext | 见 API 示例 |
| 日志 / 偏好 | AwLog、SpDelegate | **建议迁移** aw-log / aw-store |
| 注解 | AwExperimentalApi | 实验 API 标记 |
| （内部） | PackageManagerExt | `getPackageInfo` 兼容 |

</details>

---

## API 代码示例

以下为常用写法速查；按主题折叠，便于阅读。

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
// @OptIn(AwExperimentalApi::class) timestamp.toFriendlyTime()

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
// @OptIn(AwExperimentalApi::class) context.observeNetworkState().collect { }

context.deviceBrand
Rom.isXiaomi()

context.getStreamVolume()
context.setStreamVolume(index = 5, flags = AudioManager.FLAG_SHOW_UI)

context.startServiceCompat<MyService> { }
context.startForegroundServiceCompat<MyFgService>()
```

</details>

<details>
<summary><b>应用 · 意图 · 屏幕 · Context · 系统栏 · 通知 · Meta · 系统 · 手电筒 · 振动</b></summary>

```kotlin
context.launchApp("com.tencent.mm")
context.openWirelessSettings()
context.installApk(uri)

activity.setWindowBrightness(200)
activity.setStatusBarColorAndStyle(Color.WHITE, darkStatusBarIcons = true)

context.showSimpleNotification(
    channelId = "ch", notificationId = 1, title = "Hi", text = "…",
    smallIcon = android.R.drawable.ic_dialog_info,
)
context.getApplicationMetaDataString("KEY")
val stop = context.onPrimaryClipChanged { }

context.setTorchEnabled(true)
context.vibrate(50L)
```

</details>

<details>
<summary><b>View · Color · Uri · Bitmap · Text · EditText · ImageView · Activity · 集合 · Span · 键盘 · 进程</b></summary>

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

<details>
<summary><b>AwLog · SpDelegate（建议迁移至 aw-log / aw-store）</b></summary>

```kotlin
AwLog.init(isDebug = BuildConfig.DEBUG, prefix = "App")
object Prefs : SpDelegate("prefs") { var token by string("t", "") }
```

</details>

更多参数与边界行为以 **源码 KDoc** 为准。

---

## 演示应用

- 模块：`demo`（多 Tab Fragment）。
- 功能与 Tab 对照：[demo/DEMO_MATRIX.md](demo/DEMO_MATRIX.md)。
- 工具栏菜单「演示清单」可快速浏览各 Tab。

---

## 工程与发版

| 项 | 说明 |
|----|------|
| **CI** | [.github/workflows/ci.yml](.github/workflows/ci.yml) |
| **弃用迁移** | `AwLog` → [aw-log](https://github.com/answufeng/aw-log)；`SpDelegate` → [aw-store](https://github.com/answufeng/aw-store) |
| **版本号** | 与仓库 [gradle.properties](gradle.properties) 中 `VERSION_NAME`、Git **Tag**、JitPack 保持一致 |

---

## 实验性 API

以下需 `@OptIn(AwExperimentalApi::class)`：

- `Number.dp` / `dpF` / `sp` / `spF`（`Resources.getSystem()`）
- `Long.toFriendlyTime()`（硬编码中文）
- `Context.observeNetworkState()`
- `Context.deviceSummary()`

---

## 旧版迁移（1.0 → 1.1）

| 旧 API | 新 API |
|--------|--------|
| `view.onClick { }` | `view.debounceClick { }` |
| `view.postDelayed(300L) { }` | `view.postDelay(300L) { }` |
| `context.dp2px(16f)` | `16.dpToPx(context)` |
| `deviceBrand`（顶层） | `context.deviceBrand` |
| `activity.openCamera(...)` | Activity Result API |

（旧符号多保留为 `@Deprecated`，详见 IDE 提示。）

---

## 混淆

库通过 `consumer-rules.pro` 提供 **Consumer ProGuard** 规则；宿主一般无需再抄一份。

---

## 许可证

Apache License 2.0，见 [LICENSE](LICENSE)。

---

*文档更新：2026-04-23*
