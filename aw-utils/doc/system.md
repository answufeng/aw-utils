# SystemExt — 剪贴板 / Toast / 拨号 / 分享 / 权限检查

> 源码：[SystemExt.kt](../src/main/java/com/answufeng/utils/SystemExt.kt)

## 剪贴板

```kotlin
context.copyToClipboard("文本", label = "text")
context.getClipboardText()                        // 返回 String?
val removeListener = context.onPrimaryClipChanged { /* 变化回调 */ }
removeListener()                                  // 移除监听
```

> Android 10+ 后台应用无法读取剪贴板；Android 13+ 系统自动显示"已复制"提示。

## Toast

```kotlin
context.toast("短提示")
context.toastLong("长提示")
```

## 拨号 / 浏览器

```kotlin
context.dial("10086")              // 打开拨号界面
context.openBrowser("https://...") // 打开浏览器
```

## 分享

```kotlin
context.shareText("分享内容")
context.shareFile(uri, "image/png", title = "分享到")
```

## 应用信息（已弃用）

```kotlin
context.appVersionName()    // ⚠️ 已弃用 → 使用 AppExt.getAppVersionName()
context.appVersionCode()    // ⚠️ 已弃用 → 使用 AppExt.getAppVersionCode()
context.openAppSettings()   // 打开当前应用设置页
```

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `Context.appVersionName()` | `Context.getAppVersionName()` (AppExt) |
| `Context.appVersionCode()` | `Context.getAppVersionCode()` (AppExt) |

## 权限检查

```kotlin
context.hasPermission(Manifest.permission.CAMERA)           // 单个
context.isPermissionGranted(Manifest.permission.CAMERA, ...) // 多个
```

## 相关

- [IntentExt](intent.md) — 更多系统跳转
- [KeyboardExt](keyboard.md) — 软键盘控制
