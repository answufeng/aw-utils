# Changelog

## 1.1.0 (Unreleased)

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

- StringExt：手机号/邮箱/身份证/URL 校验、脱敏、MD5/SHA-256、orDefault、ellipsize
- DateExt：格式化、解析、友好时间、日期比较（isToday/isYesterday/isSameDay）
- FileExt：大小格式化、MD5/SHA-256、安全删除、流写入、totalSize、readTextOrNull
- NetworkExt：网络状态判断、Flow 状态监听、NetworkType 枚举
- DeviceExt：品牌、型号、系统版本、设备摘要
- ContextExt：dp/sp 转换、屏幕尺寸、状态栏/导航栏高度
- EncodeExt：Base64、Hex 编解码
- SystemExt：剪贴板、键盘、Toast、应用信息、权限检查、系统 Intent
- ViewExt：防抖点击、可见性控制、toggleVisibility
- ActivityExt：泛型启动、安全参数获取（extraOrNull/extraOrDefault）
- CollectionExt：ifNotEmpty、safeJoinToString
- AwLog：轻量日志工具（推荐迁移至 aw-log）
- SpDelegate：SharedPreferences 属性委托（推荐迁移至 aw-store）
