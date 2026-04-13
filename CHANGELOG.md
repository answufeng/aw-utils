# Changelog

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
