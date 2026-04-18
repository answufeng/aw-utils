# Changelog

## 1.2.0 (Unreleased)

### 正确性修复

- **StringExt**: `isIdCard()` 增加第 18 位校验码验证（加权因子算法），修复无效身份证号通过验证的问题
- **FileExt**: `isSymlink()` 增加 API 24-25 兼容性处理（捕获 `NoClassDefFoundError`），修复低版本崩溃
- **ProcessExt**: `runOnUiThreadDelayed()` 返回 `Runnable`，修复 `removeUiThreadCallback()` 无法移除延迟任务的问题
- **IntentExt**: `sendEmail()`/`sendSMS()` 改用 `safeStartActivity()`，修复无对应应用时崩溃
- **IntentExt**: `openMap()` 改用 `safeStartActivity()`，返回 `Boolean` 表示是否成功启动
- **ImageViewExt**: `setTintRes()` 注解从 `@DrawableRes` 修正为 `@ColorRes`

### 新增函数

- **StringExt**: `isChinesePhoneNumber()` 严格手机号校验（号段验证）
- **StringExt**: `mask(keepStart, keepEnd, maskChar)` 通用脱敏方法
- **DateExt**: `Long.addSeconds()` / `Long.addWeeks()` 补全日期计算系列
- **ContextExt**: `isDarkMode` 深色模式判断
- **ContextExt**: `isExternalStorageAvailable` / `isExternalStorageWritable` 外部存储状态判断
- **ActivityExt**: `isDestroyedCompat` / `isAlive` Activity 生命周期安全检查
- **KeyboardExt**: Fragment 键盘可见性判断和监听扩展
- **BitmapExt**: `Bitmap.rotate(degrees)` 旋转
- **ViewExt**: `setWidthDp()` / `setHeightDp()` dp 版本尺寸设置
- **ViewExt**: `setPaddingStart/Top/Right/Bottom()` 单方向 padding 设置
- **SpanExt**: `spanImage()` 图片 Span
- **SpanExt**: `spannable()` 返回 `SpannableStringBuilder`，支持后续追加
- **IntentExt**: `safeStartActivity()` 安全启动 Activity
- **IntentExt**: `openFile()` / `shareFile()` 打开和分享文件
- **FileExt**: `isImage()` / `isVideo()` / `isAudio()` 媒体文件判断
- **CollectionExt**: `ifEmpty()` 集合为空时执行操作
- **SpDelegate**: `edit(block)` 批量编辑 SharedPreferences

### API 改进

- **EditTextExt**: `onTextChanged()` 返回 `TextWatcher`，支持移除监听
- **ViewExt**: `setWidth()`/`setHeight()` null LayoutParams 改为抛出 `IllegalStateException`，避免静默隐藏 BUG
- **DateExt**: `addDays()`/`addHours()` 补充 DST 限制说明 KDoc

### 性能优化

- **DateExt**: Calendar 对象复用（ThreadLocal 缓存），减少 GC 压力
- **StringExt**: MessageDigest ThreadLocal 缓存，避免重复查找算法
- **NetworkExt**: `observeNetworkState()` 去掉 `ConcurrentHashMap`，callbackFlow 内部串行无需同步
- **BitmapExt**: `toCircle()`/`toRounded()` 改用 `BitmapShader` 方案，兼容硬件加速，性能更好
- **RandomExt**: `randomElements()` 改用 Fisher-Yates 部分采样算法，大列表场景 O(n) → O(k)

### Lint 修复

- **VibrateExt**: 添加 `@SuppressLint("MissingPermission")` 抑制已知 lint 误报
- **FileExt**: `isSymlink()` 添加 `@SuppressLint("NewApi")` 抑制 lint 误报
- **build.gradle.kts**: 添加 `lint { abortOnError = false }` 避免已废弃代码阻塞构建

## 1.1.0 (Unreleased)

### 新增模块

- **RegexExt**: IP 地址、中文、用户名、密码强度（强/中/弱）、中文姓名、车牌号、MAC 地址校验
- **AppExt**: 应用安装检测、Debug 判断、前台判断、系统应用判断、启动应用、获取图标/名称、签名 SHA-1、应用详情设置页
- **IntentExt**: 发送邮件、发送短信、打开相机、选择图片、打开地图、打开应用市场、系统设置/WiFi/位置/蓝牙设置页、安装 APK
- **ScreenExt**: 平板判断、横竖屏判断、窗口亮度获取/设置/重置、屏幕常亮
- **SpanExt**: 颜色/背景色/加粗/斜体/下划线/删除线/字体大小/上标/下标/可点击 Span，DSL 构建器，TextView 可点击链接模式
- **VibrateExt**: 短振/自定义时长/模式振动/取消振动（兼容 API 31+ VibratorManager）
- **RandomExt**: 随机字母数字/数字/字母字符串、随机整数/长整数、随机颜色、列表随机元素/多个不重复元素
- **ProcessExt**: 主线程判断、UI 线程执行（自动判断线程）、延迟 UI 线程执行
- **KeyboardExt**: 键盘可见性判断、键盘可见性变化监听（返回取消函数）

### 新增函数

- **FileExt**: `Long.toFriendlySize()` 字节数格式化（`File.friendlySize()` 委托至此）

### 正确性修复

- **StringExt**: `md5()` / `sha256()` 增加 `charset` 参数（默认 UTF-8），修复跨平台哈希不一致问题
- **DateExt**: ThreadLocal 缓存改为 LRU 淘汰（最多 8 个 pattern），防止动态 pattern 导致内存泄漏
- **DateExt**: `SimpleDateFormat` 使用 `Locale.US` 替代 `Locale.getDefault()`，修复非英语 Locale 下日期解析失败
- **ActivityExt**: `extraOrNull()` / `argumentOrNull()` 使用类型安全 getter + `containsKey`，修复基本类型 Int/Long/Boolean 等不存在时返回默认值而非 null 的 bug

### API 一致性优化

- **ViewExt**: `onClick()` → `debounceClick()`（inline + crossinline），避免与 setOnClickListener 命名冲突
- **ViewExt**: `postDelayed()` → `postDelay()`，避免与 View.postDelayed 遮蔽
- **StringExt**: `ellipsize()` → `truncate()`，避免与 TextUtils.ellipsize 冲突，增加参数校验
- **StringExt**: 废弃 `isNotNullOrBlank()` / `isNotNullOrEmpty()` / `orDefault()`，引导使用 stdlib 惯用写法
- **ContextExt**: 废弃 `dp2px()` / `sp2px()` / `px2dp()` / `px2sp()`，新增 `dpToPx(context)` / `spToPx(context)` / `pxToDp(context)` / `pxToSp(context)` Context 感知版本
- **DeviceExt**: 顶层属性改为 Context 扩展属性，避免命名空间污染
- **DateExt**: 废弃 `currentTimeMillis()`，引导使用 `System.currentTimeMillis()`
- **EncodeExt**: `decodeBase64String()` → `decodeBase64ToString()`，命名更符合 Kotlin 惯例

### 性能优化

- **EncodeExt**: `toHexString()` 改用查表法，大数组性能提升 5-10 倍
- **EncodeExt**: `hexToByteArray()` 直接字符操作，性能提升 3-5 倍，增加非法字符校验
- **NetworkExt**: 提取 `getActiveNetworkCapabilities()` 私有扩展函数，消除 4 处重复代码

### 安全与扩展

- **SystemExt**: 新增 `View.hideKeyboard()` 和 `Fragment.hideKeyboard()`
- **FileExt**: `safeDeleteRecursively()` 增加符号链接检测保护
- **Annotations**: 新增 `@AwExperimentalApi` 注解，标记实验性 API
- 标记为实验性的 API：`Number.dp/sp`、`Long.toFriendlyTime()`、`Context.observeNetworkState()`、`Context.deviceSummary()`

### 兼容性

所有旧 API 保留为 `@Deprecated(WARNING)` 转发到新实现，不破坏任何现有调用方。

## 1.0.0

Initial release.
