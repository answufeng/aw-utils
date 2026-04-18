package com.answufeng.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

/**
 * 发送邮件。
 *
 * @param email 收件人邮箱
 * @param subject 邮件主题
 * @param text 邮件正文
 */
fun Context.sendEmail(email: String, subject: String = "", text: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(intent, "选择邮件应用"))
}

/**
 * 发送短信（打开短信应用）。
 *
 * @param phoneNumber 手机号
 * @param message 短信内容
 */
fun Context.sendSMS(phoneNumber: String, message: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("smsto:$phoneNumber")
        putExtra("sms_body", message)
    }
    startActivity(intent)
}

/**
 * 打开相机拍照。
 *
 * **已废弃**：使用 `startActivityForResult`，API 30 已废弃。
 * 推荐使用 Activity Result API（`registerForActivityResult`）替代。
 *
 * @param requestCode 请求码
 * @param imageUri 拍照保存的 Uri
 */
@Deprecated(
    message = "Use Activity Result API (registerForActivityResult) instead",
    level = DeprecationLevel.WARNING
)
fun android.app.Activity.openCamera(imageUri: Uri, requestCode: Int) {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
        putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
    }
    startActivityForResult(intent, requestCode)
}

/**
 * 打开相册选择图片。
 *
 * **已废弃**：使用 `startActivityForResult`，API 30 已废弃。
 * 推荐使用 Activity Result API（`registerForActivityResult`）替代。
 *
 * @param requestCode 请求码
 */
@Deprecated(
    message = "Use Activity Result API (registerForActivityResult) instead",
    level = DeprecationLevel.WARNING
)
fun android.app.Activity.pickImage(requestCode: Int) {
    val intent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
    }
    startActivityForResult(intent, requestCode)
}

/**
 * 打开地图定位指定坐标。
 *
 * @param latitude 纬度
 * @param longitude 经度
 * @param label 地标名称
 */
fun Context.openMap(latitude: Double, longitude: Double, label: String = "") {
    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($label)")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(intent)
}

/**
 * 打开应用市场（当前应用）。
 *
 * @param packageName 要跳转的应用包名，默认为当前应用
 */
fun Context.openAppMarket(packageName: String = this.packageName) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
    try {
        startActivity(intent)
    } catch (_: Exception) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$packageName")))
    }
}

/**
 * 打开系统设置页面。
 */
fun Context.openSettings(action: String = android.provider.Settings.ACTION_SETTINGS) {
    startActivity(Intent(action))
}

/**
 * 打开 WiFi 设置页面。
 */
fun Context.openWifiSettings() {
    startActivity(Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
}

/**
 * 打开位置设置页面。
 */
fun Context.openLocationSettings() {
    startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
}

/**
 * 打开蓝牙设置页面。
 */
fun Context.openBluetoothSettings() {
    startActivity(Intent(android.provider.Settings.ACTION_BLUETOOTH_SETTINGS))
}

/**
 * 安装 APK 文件。
 *
 * 注意：
 * - Android 7+ 需要使用 FileProvider 提供 content Uri
 * - Android 8+ 需要声明 `REQUEST_INSTALL_PACKAGES` 权限
 * - Android 12+ 安装流程需要用户确认
 *
 * @param uri APK 文件的 content Uri
 */
fun Context.installApk(uri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/vnd.android.package-archive")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
}
