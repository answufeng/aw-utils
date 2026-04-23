package com.answufeng.utils

import android.content.Context
import android.media.AudioManager

private fun Context.audioManager(): AudioManager =
    getSystemService(Context.AUDIO_SERVICE) as AudioManager

/**
 * 当前媒体音量档位（非百分比），[streamType] 默认 [AudioManager.STREAM_MUSIC]。
 */
fun Context.getStreamVolume(streamType: Int = AudioManager.STREAM_MUSIC): Int =
    audioManager().getStreamVolume(streamType)

/**
 * 指定流的最大音量档位。
 */
fun Context.getStreamMaxVolume(streamType: Int = AudioManager.STREAM_MUSIC): Int =
    audioManager().getStreamMaxVolume(streamType)

/**
 * 设置流音量；通常需在 Manifest 声明 `MODIFY_AUDIO_SETTINGS`（非危险权限，安装时授予）。
 *
 * @param flags 如 [AudioManager.FLAG_SHOW_UI]
 */
fun Context.setStreamVolume(
    streamType: Int = AudioManager.STREAM_MUSIC,
    index: Int,
    flags: Int = 0,
) {
    audioManager().setStreamVolume(streamType, index.coerceIn(0, getStreamMaxVolume(streamType)), flags)
}
