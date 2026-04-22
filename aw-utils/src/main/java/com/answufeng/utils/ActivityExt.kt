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
    safeStartActivity(intent)
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
    safeStartActivity(Intent(this, T::class.java).apply(block))
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
 * 获取 Activity Intent 中的 Extra 值，不存在或类型不匹配时返回 null。
 *
 * 对于基本类型（Int、Long、Boolean 等），使用 `containsKey` 判断是否存在，
 * 避免不存在时返回默认值 0/false 而非 null 的问题。
 */
inline fun <reified T> Activity.extraOrNull(key: String): T? {
    val extras = intent?.extras ?: return null
    @Suppress("DEPRECATION")
    return when (T::class) {
        String::class -> extras.getString(key) as? T
        Int::class -> if (extras.containsKey(key)) extras.getInt(key) as? T else null
        Long::class -> if (extras.containsKey(key)) extras.getLong(key) as? T else null
        Boolean::class -> if (extras.containsKey(key)) extras.getBoolean(key) as? T else null
        Float::class -> if (extras.containsKey(key)) extras.getFloat(key) as? T else null
        Double::class -> if (extras.containsKey(key)) extras.getDouble(key) as? T else null
        else -> extras.get(key) as? T
    }
}

/**
 * 获取 Fragment Arguments 中的值，不存在或类型不匹配时返回 null。
 *
 * 对于基本类型（Int、Long、Boolean 等），使用 `containsKey` 判断是否存在，
 * 避免不存在时返回默认值 0/false 而非 null 的问题。
 */
inline fun <reified T> Fragment.argumentOrNull(key: String): T? {
    val args = arguments ?: return null
    @Suppress("DEPRECATION")
    return when (T::class) {
        String::class -> args.getString(key) as? T
        Int::class -> if (args.containsKey(key)) args.getInt(key) as? T else null
        Long::class -> if (args.containsKey(key)) args.getLong(key) as? T else null
        Boolean::class -> if (args.containsKey(key)) args.getBoolean(key) as? T else null
        Float::class -> if (args.containsKey(key)) args.getFloat(key) as? T else null
        Double::class -> if (args.containsKey(key)) args.getDouble(key) as? T else null
        else -> args.get(key) as? T
    }
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

/**
 * 判断 Activity 是否已销毁（API 17+）。
 *
 * 在异步回调中使用 Activity 前应先检查此属性，
 * 避免在 Activity 销毁后执行 UI 操作导致崩溃。
 */
val Activity.isDestroyedCompat: Boolean
    get() = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
        isDestroyed
    } else {
        false
    }

/**
 * 判断 Activity 是否仍然存活（未销毁且未 finishing）。
 */
val Activity.isAlive: Boolean
    get() = !isFinishing && !isDestroyedCompat
