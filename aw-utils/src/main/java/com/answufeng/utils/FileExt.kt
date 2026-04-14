package com.answufeng.utils

import java.io.File
import java.io.InputStream
import java.security.MessageDigest

/**
 * 将文件大小格式化为友好可读的字符串。
 *
 * 如 `1536` → `"1.5 KB"`，`1073741824` → `"1.00 GB"`。
 */
fun File.friendlySize(): String {
    val size = length().toDouble()
    return when {
        size < 1024 -> "%.0f B".format(size)
        size < 1024 * 1024 -> "%.1f KB".format(size / 1024)
        size < 1024 * 1024 * 1024 -> "%.1f MB".format(size / (1024 * 1024))
        else -> "%.2f GB".format(size / (1024 * 1024 * 1024))
    }
}

/**
 * 文件扩展名（小写），如 `"jpg"`、`"png"`。
 */
val File.extensionName: String get() = extension.lowercase()

/**
 * 安全递归删除文件或目录。
 *
 * @return 是否删除成功
 */
fun File.safeDeleteRecursively(): Boolean {
    return if (isDirectory) {
        (listFiles()?.all { it.safeDeleteRecursively() } ?: true) && delete()
    } else {
        delete()
    }
}

/**
 * 确保父目录存在，不存在则创建。
 *
 * @return 当前文件对象（便于链式调用）
 */
fun File.ensureParentDir(): File {
    parentFile?.let { if (!it.exists()) it.mkdirs() }
    return this
}

private fun File.digest(algorithm: String): String {
    val messageDigest = MessageDigest.getInstance(algorithm)
    inputStream().use { stream ->
        val buffer = ByteArray(8192)
        var read: Int
        while (stream.read(buffer).also { read = it } != -1) {
            messageDigest.update(buffer, 0, read)
        }
    }
    return messageDigest.digest().toHexString()
}

/**
 * 计算文件的 MD5 摘要（32 位小写十六进制）。
 */
fun File.md5(): String = digest("MD5")

/**
 * 计算文件的 SHA-256 摘要（64 位小写十六进制）。
 */
fun File.sha256(): String = digest("SHA-256")

/**
 * 计算文件或目录的总大小（字节）。
 *
 * 若为目录，递归计算所有子文件大小之和。
 */
fun File.totalSize(): Long {
    return if (isDirectory) {
        listFiles()?.sumOf { it.totalSize() } ?: 0L
    } else {
        length()
    }
}

/**
 * 将 [InputStream] 写入文件，自动创建父目录。
 *
 * 流会在写入完成后关闭。
 */
fun InputStream.writeToFile(file: File) {
    file.ensureParentDir()
    file.outputStream().use { out ->
        copyTo(out)
    }
}

/**
 * 读取文件文本内容，失败时返回 null。
 *
 * @param charset 字符编码，默认 UTF-8
 */
fun File.readTextOrNull(charset: java.nio.charset.Charset = Charsets.UTF_8): String? {
    return try {
        readText(charset)
    } catch (_: Exception) {
        null
    }
}
