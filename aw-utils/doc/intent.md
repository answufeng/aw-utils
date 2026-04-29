# IntentExt — 邮件 / 短信 / 地图 / 应用市场 / 系统设置 / 安装 APK / 文件操作

> 源码：[IntentExt.kt](../src/main/java/com/answufeng/utils/IntentExt.kt)

## 通讯

```kotlin
context.sendEmail("a@b.com", subject = "主题", text = "正文")
context.sendSMS("10086", message = "内容")
```

## 地图

```kotlin
context.openMap(39.9, 116.4, label = "天安门")
```

## 应用市场

```kotlin
context.openAppMarket()                      // 当前应用
context.openAppMarket("com.example.app")     // 指定包名
```

> 优先打开应用市场，失败则回退到 Google Play 网页。

## 系统设置

```kotlin
context.openSettings()                  // 系统设置
context.openWifiSettings()              // WiFi 设置
context.openWirelessSettings()          // 无线网络设置
context.openLocationSettings()          // 位置设置
context.openBluetoothSettings()         // 蓝牙设置
context.openAccessibilitySettings()     // 无障碍设置
context.openDeveloperSettings()         // 开发者选项
context.openDateSettings()              // 日期和时间
context.openSoundSettings()             // 声音/通知
context.openDisplaySettings()           // 显示
context.openStorageSettings()           // 存储
context.openAboutPhoneSettings()        // 关于手机
```

## 安装 APK

```kotlin
context.installApk(uri)  // 需 FileProvider + REQUEST_INSTALL_PACKAGES 权限
```

> Android 7+ 需 FileProvider；Android 8+ 需 `REQUEST_INSTALL_PACKAGES` 权限。

## 文件操作

```kotlin
context.openFile(uri, "application/pdf")  // 第三方应用打开文件
context.shareFile(uri, "image/png")       // 分享文件
```

## 安全启动 Activity

```kotlin
context.safeStartActivity(intent)  // 先检查 resolveActivity，非 Activity Context 自动加 NEW_TASK
```

> 所有跳转扩展内部均使用 `safeStartActivity`，返回 `Boolean` 表示是否成功启动。

## 已弃用 API

| 弃用 API | 替代方案 |
|----------|----------|
| `Activity.openCamera()` | Activity Result API |
| `Activity.pickImage()` | Activity Result API |
