package com.answufeng.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * 复制文本到剪贴板。
 *
 * Android 13+ 系统会自动显示"已复制到剪贴板"提示，
 * 无需额外处理。
 *
 * @param text 要复制的文本
 * @param label 剪贴板标签，默认 `"text"`
 */
fun Context.copyToClipboard(text: String, label: String = "text") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(label, text))
}

/**
 * 获取剪贴板中的文本。
 *
 * 注意：Android 10+ 后台应用无法读取剪贴板内容，
 * 仅在应用处于前台时有效。
 *
 * @return 剪贴板文本，无内容时返回 null
 */
fun Context.getClipboardText(): String? {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    return clipboard.primaryClip?.getItemAt(0)?.text?.toString()
}

/** 弹出软键盘。 */
fun EditText.showKeyboard() {
    requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/** 隐藏软键盘（使用 View 的 windowToken）。 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

/** 隐藏软键盘（使用当前焦点 View 或 decorView）。 */
fun Activity.hideKeyboard() {
    val view = currentFocus ?: window.decorView
    view.hideKeyboard()
}

/** 隐藏软键盘（使用 Fragment 的 root view）。 */
fun Fragment.hideKeyboard() {
    view?.hideKeyboard()
}

/** 显示短 Toast。 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/** 显示长 Toast。 */
fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/** 拨打电话（打开拨号界面）。 */
fun Context.dial(phone: String) {
    safeStartActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
}

/** 打开浏览器。 */
fun Context.openBrowser(url: String) {
    safeStartActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

/**
 * 分享文本。
 *
 * @param text 要分享的文本
 * @param title 选择器标题，默认 `"分享"`
 */
fun Context.shareText(text: String, title: String = "分享") {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(intent, title))
}

/** 打开当前应用的设置页。 */
fun Context.openAppSettings() {
    val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}

/** 获取应用版本名。 */
fun Context.appVersionName(): String {
    return packageManager.getPackageInfo(packageName, 0).versionName ?: ""
}

/** 获取应用版本号。 */
fun Context.appVersionCode(): Long {
    val info = packageManager.getPackageInfo(packageName, 0)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        info.longVersionCode
    } else {
        @Suppress("DEPRECATION")
        info.versionCode.toLong()
    }
}

/** 检查是否拥有指定权限。 */
fun Context.hasPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

/** 检查是否拥有所有指定权限。 */
fun Context.isPermissionGranted(vararg permissions: String): Boolean {
    return permissions.all { hasPermission(it) }
}
