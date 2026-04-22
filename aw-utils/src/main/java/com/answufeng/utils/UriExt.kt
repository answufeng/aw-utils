package com.answufeng.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import java.util.Locale

/**
 * 将 content Uri 转换为文件路径。
 *
 * 注意：并非所有 content Uri 都能转换为文件路径（如 Google Drive 等云存储），
 * 转换失败时返回 null。
 *
 * @return 文件绝对路径，转换失败返回 null
 */
fun Uri.toFilePath(context: Context): String? {
    if (scheme == ContentResolver.SCHEME_FILE) {
        return path
    }
    if (scheme != ContentResolver.SCHEME_CONTENT) {
        return null
    }
    return try {
        context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(android.provider.MediaStore.MediaColumns.DATA)
                if (columnIndex >= 0) cursor.getString(columnIndex) else null
            } else null
        }
    } catch (_: Exception) {
        null
    }
}

/**
 * 从 Uri 获取文件名。
 *
 * 优先从 ContentResolver 查询 DISPLAY_NAME，
 * 查询失败时从 Uri 的 lastPathSegment 推断。
 *
 * @return 文件名，获取失败返回 null
 */
fun Uri.getFileName(context: Context): String? {
    if (scheme == ContentResolver.SCHEME_FILE) {
        return lastPathSegment
    }
    if (scheme != ContentResolver.SCHEME_CONTENT) {
        return lastPathSegment
    }
    return try {
        context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (columnIndex >= 0) cursor.getString(columnIndex) else null
            } else null
        }
    } catch (_: Exception) {
        lastPathSegment
    }
}

/**
 * 从 Uri 获取 MIME 类型。
 *
 * @return MIME 类型字符串，无法确定时返回 "application/octet-stream"
 */
fun Uri.getMimeType(context: Context): String {
    if (scheme == ContentResolver.SCHEME_CONTENT) {
        val type = context.contentResolver.getType(this)
        if (!type.isNullOrBlank()) return type
    }
    val extension = MimeTypeMap.getFileExtensionFromUrl(toString())
    if (!extension.isNullOrBlank()) {
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.lowercase())
            ?: "application/octet-stream"
    }
    return "application/octet-stream"
}

/**
 * 粗略判断 Uri 是否指向设备侧可访问内容（非云端流式等）。
 *
 * - `file` 方案视为本地。
 * - `content` 方案默认视为本地，但排除部分已知的云端/跨设备 Provider（如 Google 文档）。
 *
 * 不能替代 [toFilePath]：部分本地 content Uri 仍无法映射到文件路径。
 */
fun Uri.isLocalFile(): Boolean {
    return when (scheme) {
        ContentResolver.SCHEME_FILE -> true
        ContentResolver.SCHEME_CONTENT -> {
            val h = host?.lowercase(Locale.ROOT) ?: return false
            REMOTE_CONTENT_AUTHORITY_PREFIXES.none { prefix -> h.startsWith(prefix) }
        }
        else -> false
    }
}

private val REMOTE_CONTENT_AUTHORITY_PREFIXES = listOf(
    "com.google.android.apps.docs",
    "com.google.android.apps.photos",
)
