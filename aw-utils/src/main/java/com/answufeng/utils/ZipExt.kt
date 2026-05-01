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

private const val DEFAULT_MAX_ZIP_ENTRIES = 10_000
private const val DEFAULT_MAX_UNZIP_BYTES = 256L * 1024L * 1024L

/**
 * 将 zip 解压到目标目录；对条目路径做 **Zip Slip** 校验，拒绝写到 [destDir] 之外。
 *
 * @param maxEntries 最多处理的条目数（含目录），防止 zip 炸弹条目风暴。
 * @param maxExpandedBytes 解压写入的字节上限（近似），防止磁盘占满。
 * @return 是否完成解压
 */
fun File.unzipToDirectory(
    destDir: File,
    maxEntries: Int = DEFAULT_MAX_ZIP_ENTRIES,
    maxExpandedBytes: Long = DEFAULT_MAX_UNZIP_BYTES,
): Boolean {
    if (!isFile) return false
    return try {
        destDir.mkdirs()
        val canonicalDest = destDir.canonicalFile
        var entryIndex = 0
        var totalWritten = 0L
        ZipInputStream(FileInputStream(this)).use { zis ->
            var entry = zis.nextEntry
            while (entry != null) {
                if (++entryIndex > maxEntries) return false
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
                    val est = entry.size.takeIf { it > 0 } ?: (4L * 1024L)
                    if (totalWritten + est > maxExpandedBytes) return false
                    FileOutputStream(outFile).use { fos ->
                        val n = zis.copyTo(fos)
                        totalWritten += n
                        if (totalWritten > maxExpandedBytes) return false
                    }
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
