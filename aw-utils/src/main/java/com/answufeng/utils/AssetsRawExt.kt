package com.answufeng.utils

import android.content.Context
import androidx.annotation.RawRes
import java.io.File
import java.nio.charset.Charset
import kotlin.text.Charsets

/**
 * 从 `assets` 读取全部文本。
 *
 * @param assetPath 资产路径，如 `"config.json"`、`"subdir/data.txt"`
 */
fun Context.readAssetText(assetPath: String, charset: Charset = Charsets.UTF_8): String {
    return assets.open(assetPath).bufferedReader(charset).use { it.readText() }
}

/**
 * 从 `assets` 按行读取文本。
 */
fun Context.readAssetLines(assetPath: String, charset: Charset = Charsets.UTF_8): List<String> {
    return assets.open(assetPath).bufferedReader(charset).use { it.readLines() }
}

/**
 * 从 `res/raw` 读取全部文本。
 */
fun Context.readRawText(@RawRes rawResId: Int, charset: Charset = Charsets.UTF_8): String {
    return resources.openRawResource(rawResId).bufferedReader(charset).use { it.readText() }
}

/**
 * 将 `assets` 中的文件拷贝到本地 [dest]（自动创建父目录）。
 *
 * @return 是否拷贝成功
 */
fun Context.copyAssetToFile(assetPath: String, dest: File): Boolean {
    return try {
        dest.ensureParentDir()
        assets.open(assetPath).use { input ->
            dest.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        true
    } catch (_: Exception) {
        false
    }
}
