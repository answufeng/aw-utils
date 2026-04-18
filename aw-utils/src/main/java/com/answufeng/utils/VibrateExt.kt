package com.answufeng.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * 触发短振动（约 50ms）。
 *
 * 需要 `android.permission.VIBRATE` 权限。
 */
fun Context.vibrate() {
    val vibrator = getVibrator() ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(50L, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(50L)
    }
}

/**
 * 触发自定义时长振动。
 *
 * 需要 `android.permission.VIBRATE` 权限。
 *
 * @param milliseconds 振动时长（毫秒）
 */
fun Context.vibrate(milliseconds: Long) {
    val vibrator = getVibrator() ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(milliseconds)
    }
}

/**
 * 触发模式振动。
 *
 * 需要 `android.permission.VIBRATE` 权限。
 *
 * @param pattern 振动模式，偶数索引为静止时长，奇数索引为振动时长
 * @param repeat 重复次数，-1 为不重复
 */
fun Context.vibrate(pattern: LongArray, repeat: Int = -1) {
    val vibrator = getVibrator() ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createWaveform(pattern, repeat))
    } else {
        @Suppress("DEPRECATION")
        vibrator.vibrate(pattern, repeat)
    }
}

/**
 * 取消振动。
 */
fun Context.cancelVibrate() {
    getVibrator()?.cancel()
}

@Suppress("DEPRECATION")
private fun Context.getVibrator(): Vibrator? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager)?.defaultVibrator
    } else {
        getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }
}
