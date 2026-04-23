package com.answufeng.utils

import android.content.Context
import java.io.File

/**
 * 清空 [Context.getCacheDir] 下的直接子项（不删除 cache 目录本身）。
 *
 * 常用于「设置页清理缓存」；不会清理 externalCache。
 *
 * @return 是否未抛出异常地完成遍历删除（单个文件删失败不会中断，整体仍可能返回 true）。
 */
fun Context.clearInternalCacheChildren(): Boolean = cacheDir.clearDirectoryChildren()

/**
 * 清空 [Context.getFilesDir] 下的直接子项（不删除 files 目录本身）。
 */
fun Context.clearInternalFilesChildren(): Boolean = filesDir.clearDirectoryChildren()

/**
 * 清空 [Context.getExternalCacheDir] 下的直接子项；若外置缓存目录不可用则返回 `false`。
 */
fun Context.clearExternalCacheChildren(): Boolean {
    val dir = externalCacheDir ?: return false
    return dir.clearDirectoryChildren()
}

/**
 * 删除当前目录下的所有子文件与子目录（不删除本目录）。
 */
fun File.clearDirectoryChildren(): Boolean {
    return try {
        listFiles()?.forEach { child ->
            if (child.isDirectory) {
                child.deleteRecursively()
            } else {
                child.delete()
            }
        }
        true
    } catch (_: Exception) {
        false
    }
}
