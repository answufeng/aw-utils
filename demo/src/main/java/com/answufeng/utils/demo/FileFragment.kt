package com.answufeng.utils.demo

import com.answufeng.utils.*

class FileFragment : BaseDemoFragment() {

    override fun setupDemo() {
        val tempFile = requireContext().cacheDir.resolve("test.txt").apply { writeText("hello aw-utils") }

        addTitle("📁 文件信息")
        addLog("文件大小: ${tempFile.friendlySize()}")
        addLog("扩展名: ${tempFile.extensionName}")
        addLog("MD5: ${tempFile.md5()}")
        addLog("SHA256: ${tempFile.sha256().take(16)}...")
        addLog("缓存目录大小: ${requireContext().cacheDir.totalSize().toFriendlySize()}")

        addTitle("📦 文件操作")
        val copyTarget = requireContext().cacheDir.resolve("test_copy.txt")
        val copyResult = tempFile.copyTo(copyTarget)
        addLog("复制结果: $copyResult")
        addLog("复制文件存在: ${copyTarget.exists()}")

        val moveTarget = requireContext().cacheDir.resolve("moved/test.txt")
        val moveResult = copyTarget.moveTo(moveTarget)
        addLog("移动结果: $moveResult")
        addLog("移动文件存在: ${moveTarget.exists()}")

        addTitle("🔒 编码")
        val base64 = "hello".encodeBase64()
        addLog("Base64编码: $base64")
        addLog("Base64解码: ${base64.decodeBase64ToString()}")
        val hex = byteArrayOf(0x0A, 0xFF.toByte()).toHexString()
        addLog("Hex编码: $hex")
        addLog("Hex解码: ${hex.hexToByteArray().toList()}")
    }
}
