# NotificationExt — 通知渠道 / 简单通知 / 进度通知 / 大文本通知

> 源码：[NotificationExt.kt](../src/main/java/com/answufeng/utils/NotificationExt.kt)

## 权限

Android 13+ 需声明 `POST_NOTIFICATIONS` 并获取运行时权限。

## 通知状态

```kotlin
context.areNotificationsEnabled()  // 用户是否允许通知
```

## 创建渠道

```kotlin
context.ensureNotificationChannel(
    channelId = "messages",
    channelName = "消息通知",
    importance = NotificationManager.IMPORTANCE_HIGH
)
```

> Android 8.0+ 有效，低版本无操作。渠道已存在则不变。

## 发送简单通知

```kotlin
context.showSimpleNotification(
    channelId = "messages",
    notificationId = 1,
    title = "新消息",
    text = "你有一条新消息",
    smallIcon = R.drawable.ic_notification,
    channelName = "消息通知"  // 仅首次创建渠道时生效
)
```

> 内部自动调用 `ensureNotificationChannel`。

## 取消通知

```kotlin
context.cancelNotification(1)
```

## 进度通知

```kotlin
// 下载进度
context.showProgressNotification(
    channelId = "download",
    notificationId = 100,
    title = "下载中",
    text = "正在下载文件...",
    max = 100,
    progress = 45,
    smallIcon = R.drawable.ic_notification
)

// 不确定进度（加载动画）
context.showProgressNotification(
    channelId = "download",
    notificationId = 100,
    title = "准备中",
    text = "正在准备下载...",
    max = 0,
    progress = 0,
    smallIcon = R.drawable.ic_notification,
    indeterminate = true
)
```

> 进度完成时（progress >= max）自动取消 `ongoing` 标志，通知可被用户滑动移除。

## 大文本通知

```kotlin
context.showBigTextNotification(
    channelId = "messages",
    notificationId = 200,
    title = "新消息",
    text = "长文本预览...",
    bigText = "这里是完整的很长很长的文本内容，展开通知后可以看到全部内容...",
    smallIcon = R.drawable.ic_notification
)
```

> 折叠时显示 `text`，展开后显示 `bigText` 完整内容。
