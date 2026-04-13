package com.answufeng.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Context.startActivity(extras: Bundle? = null) {
    val intent = Intent(this, T::class.java)
    extras?.let { intent.putExtras(it) }
    startActivity(intent)
}

inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(block))
}

inline fun <reified T : Activity> Fragment.startActivity(extras: Bundle? = null) {
    requireContext().startActivity<T>(extras)
}

inline fun <reified T : Activity> Fragment.startActivity(block: Intent.() -> Unit) {
    requireContext().startActivity<T>(block)
}

fun Activity.finishWithResult(resultCode: Int, data: Intent? = null) {
    setResult(resultCode, data)
    finish()
}

@Suppress("DEPRECATION")
inline fun <reified T> Activity.extraOrNull(key: String): T? {
    return intent?.extras?.get(key) as? T
}

@Suppress("DEPRECATION")
inline fun <reified T> Fragment.argumentOrNull(key: String): T? {
    return arguments?.get(key) as? T
}

inline fun <reified T> Activity.extraOrDefault(key: String, default: T): T {
    return extraOrNull(key) ?: default
}

inline fun <reified T> Fragment.argumentOrDefault(key: String, default: T): T {
    return argumentOrNull(key) ?: default
}
