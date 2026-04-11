# aw-utils

Android 工具扩展库，零业务逻辑，纯 Kotlin 扩展函数，覆盖字符串、日期、文件、网络、设备、编解码等常用场景。

## 引入

```kotlin
dependencies {
    implementation("com.github.answufeng:aw-utils:1.0.0")
}
```

## 功能特性

- StringExt：手机号/邮箱/身份证校验、脱敏、MD5/SHA-256
- DateExt：格式化、解析、友好时间、日期比较
- FileExt：大小格式化、MD5/SHA-256、安全删除、流写入
- NetworkExt：网络状态判断、Flow 状态监听
- DeviceExt：品牌、型号、系统版本
- ContextExt：dp/sp 转换、屏幕尺寸
- EncodeExt：Base64、Hex 编解码
- SystemExt：剪贴板、键盘、Toast、应用信息、系统 Intent
- ViewExt：防抖点击、可见性控制
- ActivityExt：泛型启动、安全参数获取
- CollectionExt：ifNotEmpty、safeJoinToString

## 使用示例

```kotlin
// 字符串校验与脱敏
"13812345678".isPhoneNumber()  // true
"13812345678".maskPhone()       // 138****5678
"test@mail.com".isEmail()      // true

// 日期格式化
System.currentTimeMillis().toFriendlyTime()  // "3分钟前"

// 文件操作
file.md5()
file.friendlySize()  // "1.5 MB"

// 网络检测
context.isNetworkAvailable()
context.observeNetworkState().collect { /* 响应式 */ }

// 设备信息
deviceBrand   // "Xiaomi"
deviceModel   // "MI 11"

// 防抖点击
view.onClick(interval = 500) { /* 处理点击 */ }
```

## 许可证

Apache License 2.0，详见 [LICENSE](LICENSE)。
