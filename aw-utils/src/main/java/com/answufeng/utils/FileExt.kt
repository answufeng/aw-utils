package com.answufeng.utils

import java.io.File
import java.io.InputStream
import java.security.MessageDigest

fun File.friendlySize(): String {
    val size = length().toDouble()
    return when {
        size < 1024 -> "%.0f B".format(size)
        size < 1024 * 1024 -> "%.1f KB".format(size / 1024)
        size < 1024 * 1024 * 1024 -> "%.1f MB".format(size / (1024 * 1024))
        else -> "%.2f GB".format(size / (1024 * 1024 * 1024))
    }
}

val File.extensionName: String get() = extension.lowercase()

fun File.safeDeleteRecursively(): Boolean {
    return if (isDirectory) {
        (listFiles()?.all { it.safeDeleteRecursively() } ?: true) && delete()
    } else {
        delete()
    }
}

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

fun File.md5(): String = digest("MD5")

fun File.sha256(): String = digest("SHA-256")

fun File.totalSize(): Long {
    return if (isDirectory) {
        listFiles()?.sumOf { it.totalSize() } ?: 0L
    } else {
        length()
    }
}

fun InputStream.writeToFile(file: File) {
    file.ensureParentDir()
    file.outputStream().use { out ->
        copyTo(out)
    }
}

fun File.readTextOrNull(charset: java.nio.charset.Charset = Charsets.UTF_8): String? {
    return try {
        readText(charset)
    } catch (_: Exception) {
        null
    }
}
