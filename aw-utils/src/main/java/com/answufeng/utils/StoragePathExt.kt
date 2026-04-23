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
