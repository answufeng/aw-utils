package com.answufeng.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * 将当前目录树打包为 zip（[File.walkTopDown] 默认不跟随符号链接，与 [File.safeDeleteRecursively] 心智接近）。
 *
 * @return 是否完成写入（中途异常则为 `false`）
 */
fun File.zipDirectoryTo(targetZip: File): Boolean {
    if (!isDirectory) return false
    val sourceRoot = this
    return try {
        targetZip.ensureParentDir()
        ZipOutputStream(FileOutputStream(targetZip)).use { zipOut ->
            sourceRoot.walkTopDown().forEach { candidate: File ->
                if (!candidate.isFile) return@forEach
                val entryName = candidate.relativeTo(sourceRoot).invariantSeparatorsPath
                zipOut.putNextEntry(ZipEntry(entryName))
                FileInputStream(candidate).use { input -> input.copyTo(zipOut) }
                zipOut.closeEntry()
            }
        }
        true
    } catch (_: Exception) {
        false
    }
}

/**
 * 将 zip 解压到目标目录；对条目路径做 **Zip Slip** 校验，拒绝写到 [destDir] 之外。
 *
 * @return 是否完成解压
 */
fun File.unzipToDirectory(destDir: File): Boolean {
    if (!isFile) return false
    return try {
        destDir.mkdirs()
        val canonicalDest = destDir.canonicalFile
        ZipInputStream(FileInputStream(this)).use { zis ->
            var entry = zis.nextEntry
            while (entry != null) {
                val rawName = entry.name.replace('\\', '/')
                if (rawName.isEmpty() || rawName.startsWith("/") || rawName.startsWith("../") ||
                    "/../" in "/$rawName/" || rawName.endsWith("/..")
                ) {
                    zis.closeEntry()
                    entry = zis.nextEntry
                    continue
                }
                val outFile = File(destDir, rawName)
                val canonicalOut = outFile.canonicalFile
                val destPath = canonicalDest.path
                if (!canonicalOut.path.startsWith(destPath + File.separator) &&
                    canonicalOut.path != destPath
                ) {
                    zis.closeEntry()
                    entry = zis.nextEntry
                    continue
                }
                if (entry.isDirectory) {
                    outFile.mkdirs()
                } else {
                    outFile.parentFile?.mkdirs()
                    FileOutputStream(outFile).use { fos -> zis.copyTo(fos) }
                }
                zis.closeEntry()
                entry = zis.nextEntry
            }
        }
        true
    } catch (_: Exception) {
        false
    }
}
