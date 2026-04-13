@file:Suppress("unused")

package com.answufeng.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * 启动 Activity（泛型方式）。
 *
 * ```kotlin
 * startActivity<DetailActivity>(bundleOf("id" to 42))
 * ```
 */
inline fun <reified T : Activity> Context.startActivity(extras: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    extras?.let { intent.putExtras(it) }
    startActivity(intent)
}

/**
 * 启动 Activity（带 Intent 配置）。
 *
 * ```kotlin
 * startActivity<DetailActivity> {
 *     putExtra("id", 42)
 *     flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
 * }
 * ```
 */
inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(block))
}

/**
 * 从 Fragment 启动 Activity（泛型方式）。
 */
inline fun <reified T : Activity> Fragment.startActivity(extras: Bundle? = null) {
    requireContext().startActivity<T>(extras)
}

/**
 * 从 Fragment 启动 Activity（带 Intent 配置）。
 */
inline fun <reified T : Activity> Fragment.startActivity(block: Intent.() -> Unit) {
    requireContext().startActivity<T>(block)
}

/**
 * 设置结果并关闭 Activity。
 */
fun Activity.finishWithResult(resultCode: Int, data: Intent? = null) {
    setResult(resultCode, data)
    finish()
}

/**
 * 获取 Activity Intent 中的 Extra 值，类型不匹配时返回 null。
 */
@Suppress("DEPRECATION")
inline fun <reified T> Activity.extraOrNull(key: String): T? {
    return intent?.extras?.get(key) as? T
}

/**
 * 获取 Fragment Arguments 中的值，类型不匹配时返回 null。
 */
@Suppress("DEPRECATION")
inline fun <reified T> Fragment.argumentOrNull(key: String): T? {
    return arguments?.get(key) as? T
}

/**
 * 获取 Activity Intent 中的 Extra 值，不存在时返回默认值。
 */
inline fun <reified T> Activity.extraOrDefault(key: String, default: T): T {
    return extraOrNull(key) ?: default
}

/**
 * 获取 Fragment Arguments 中的值，不存在时返回默认值。
 */
inline fun <reified T> Fragment.argumentOrDefault(key: String, default: T): T {
    return argumentOrNull(key) ?: default
}
