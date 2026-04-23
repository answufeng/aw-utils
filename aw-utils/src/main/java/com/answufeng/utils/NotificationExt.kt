package com.answufeng.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

/**
 * 当前应用通知是否可能被展示（用户未关闭通知、Android 13+ 已授权等）。
 *
 * Android 13（API 33）起发送通知需 `POST_NOTIFICATIONS` 运行时权限。
 */
fun Context.areNotificationsEnabled(): Boolean =
    NotificationManagerCompat.from(this).areNotificationsEnabled()

/**
 * 创建通知渠道（Android 8.0+）；低版本无操作。
 *
 * @param channelId 渠道 ID
 * @param channelName 用户可见的渠道名称
 * @param importance [NotificationManager] 重要性，默认 [NotificationManager.IMPORTANCE_DEFAULT]
 */
fun Context.ensureNotificationChannel(
    channelId: String,
    channelName: CharSequence,
    importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
    val channel = NotificationChannel(channelId, channelName, importance)
    val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    nm.createNotificationChannel(channel)
}

/**
 * 展示一条简单文本通知（内部会 [ensureNotificationChannel]，渠道已存在则不变）。
 *
 * @param channelName 渠道显示名（仅首次创建渠道时生效）
 * @param smallIcon 小图标资源 ID（建议白色透明剪影风格，见官方设计指南）
 */
fun Context.showSimpleNotification(
    channelId: String,
    notificationId: Int,
    title: CharSequence,
    text: CharSequence,
    @DrawableRes smallIcon: Int,
    channelName: CharSequence = channelId,
) {
    ensureNotificationChannel(channelId, channelName)
    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(smallIcon)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    NotificationManagerCompat.from(this).notify(notificationId, builder.build())
}

/**
 * 取消指定 id 的通知。
 */
fun Context.cancelNotification(notificationId: Int) {
    NotificationManagerCompat.from(this).cancel(notificationId)
}
