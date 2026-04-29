package com.answufeng.utils

import android.content.Context
import java.io.File

/**
 * 内部应用缓存目录，等价于 [Context.getCacheDir]。
 */
val Context.internalCacheDir: File get() = cacheDir

/**
 * 内部应用文件目录，等价于 [Context.getFilesDir]。
 */
val Context.internalFilesDir: File get() = filesDir

/**
 * 外部应用缓存目录；无外置存储或介质不可用时可能为 `null`。
 */
val Context.externalAppCacheDir: File? get() = externalCacheDir

/**
 * 外部应用专属文件目录。
 *
 * @param type 子类型（如 [android.os.Environment.DIRECTORY_PICTURES]），`null` 表示根目录。
 */
fun Context.externalAppFilesDir(type: String? = null): File? = getExternalFilesDir(type)

/**
 * OBB 扩展资源目录；不支持或不可用时可能为 `null`。
 */
val Context.appObbDir: File? get() = obbDir

/**
 * 应用「无需备份」内部文件目录（API 21+）；与 [Context.getNoBackupFilesDir] 一致。
 */
val Context.noBackupFilesDirPath: File get() = noBackupFilesDir

/**
 * 获取内部存储分区的总容量（字节）。
 *
 * 注意：返回的是 [filesDir] 所在分区的总容量，而非应用专属空间配额。
 */
fun Context.internalStorageTotalSize(): Long {
    return android.os.StatFs(filesDir.path).let {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            it.totalBytes
        } else {
            @Suppress("DEPRECATION")
            it.blockCount.toLong() * it.blockSize
        }
    }
}

/**
 * 获取内部存储分区的可用容量（字节）。
 *
 * 注意：返回的是 [filesDir] 所在分区的可用容量，而非应用专属空间配额。
 */
fun Context.internalStorageAvailableSize(): Long {
    return android.os.StatFs(filesDir.path).let {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            it.availableBytes
        } else {
            @Suppress("DEPRECATION")
            it.availableBlocks.toLong() * it.blockSize
        }
    }
}

/**
 * 获取外部存储分区的总容量（字节）。
 *
 * 无外部存储时返回 0。返回的是分区总容量，而非应用专属空间配额。
 */
fun Context.externalStorageTotalSize(): Long {
    val dir = externalAppCacheDir ?: return 0L
    return try {
        val stat = android.os.StatFs(dir.path)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            stat.totalBytes
        } else {
            @Suppress("DEPRECATION")
            stat.blockCount.toLong() * stat.blockSize
        }
    } catch (_: Exception) {
        0L
    }
}

/**
 * 获取外部存储分区的可用容量（字节）。
 *
 * 无外部存储时返回 0。返回的是分区可用容量，而非应用专属空间配额。
 */
fun Context.externalStorageAvailableSize(): Long {
    val dir = externalAppCacheDir ?: return 0L
    return try {
        val stat = android.os.StatFs(dir.path)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            stat.availableBytes
        } else {
            @Suppress("DEPRECATION")
            stat.availableBlocks.toLong() * stat.blockSize
        }
    } catch (_: Exception) {
        0L
    }
}
