package com.answufeng.utils.demo

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.answufeng.utils.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvLog: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLog = TextView(this).apply { textSize = 14f }
        val container = findViewById<LinearLayout>(R.id.container)
        container.addView(tvLog)

        container.addView(button("String Extensions") {
            val phone = "13812345678"
            log("isPhoneNumber: ${phone.isPhoneNumber()}")
            log("maskPhone: ${phone.maskPhone()}")

            val email = "test@example.com"
            log("isEmail: ${email.isEmail()}")
            log("maskEmail: ${email.maskEmail()}")

            val url = "https://github.com"
            log("isUrl: ${url.isUrl()}")

            val idCard = "110101199001011234"
            log("isIdCard: ${idCard.isIdCard()}")
            log("maskIdCard: ${idCard.maskIdCard()}")

            log("md5: ${"hello".md5()}")
            log("sha256: ${"hello".sha256().take(16)}...")
            log("orDefault: ${null.orDefault("默认值")}")
            log("ellipsize: ${"Hello World".ellipsize(5)}")
            log("isNotNullOrBlank: ${null.isNotNullOrBlank()}")
        })

        container.addView(button("Date Extensions") {
            val now = System.currentTimeMillis()
            log("formatDate: ${now.formatDate()}")
            log("formatDate(yyyy/MM/dd): ${now.formatDate("yyyy/MM/dd")}")
            log("isToday: ${now.isToday()}")
            log("isYesterday: ${now.isYesterday()}")
            log("toFriendlyTime(now): ${now.toFriendlyTime()}")
            log("toFriendlyTime(5min): ${(now - 5 * 60_000).toFriendlyTime()}")
            log("toFriendlyTime(3h): ${(now - 3 * 3_600_000).toFriendlyTime()}")
            log("toFriendlyTime(30h): ${(now - 30 * 3_600_000).toFriendlyTime()}")
        })

        container.addView(button("File Extensions") {
            val tempFile = cacheDir.resolve("test.txt").apply { writeText("hello aw-utils") }
            log("friendlySize: ${tempFile.friendlySize()}")
            log("extensionName: ${tempFile.extensionName}")
            log("md5: ${tempFile.md5()}")
            log("readTextOrNull: ${tempFile.readTextOrNull()}")
            log("totalSize(cacheDir): ${cacheDir.totalSize().friendlySize()}")
        })

        container.addView(button("Device Info") {
            log("Brand: $deviceBrand")
            log("Model: $deviceModel")
            log("Manufacturer: $deviceManufacturer")
            log("OS: $osVersion")
            log("SDK: $sdkVersion")
            log("Summary: ${deviceSummary()}")
        })

        container.addView(button("Network Check") {
            log("Available: ${isNetworkAvailable()}")
            log("WiFi: ${isWifiConnected()}")
            log("Type: ${getNetworkType()}")
        })

        container.addView(button("Context Extensions") {
            log("screenWidth: $screenWidth px")
            log("screenHeight: $screenHeight px")
            log("statusBarHeight: $statusBarHeight px")
            log("navigationBarHeight: $navigationBarHeight px")
            log("100dp = ${100.dp}px")
            log("14sp = ${14.sp}px")
        })

        container.addView(button("Encode Extensions") {
            val base64 = "hello".encodeBase64()
            log("Base64 encode: $base64")
            log("Base64 decode: ${base64.decodeBase64String()}")

            val hex = byteArrayOf(0x01, 0x02, 0xFF.toByte()).toHexString()
            log("Hex encode: $hex")
            log("Hex decode: ${hex.hexToByteArray().toList()}")
        })

        container.addView(button("System Extensions") {
            copyToClipboard("Copied text from aw-utils demo")
            log("Copied to clipboard")

            log("Version: ${appVersionName()}")
            log("VersionCode: ${appVersionCode()}")
            log("hasPermission(CAMERA): ${hasPermission(android.Manifest.permission.CAMERA)}")
            toast("Hello from aw-utils!")
        })

        container.addView(button("Collection Extensions") {
            val list = listOf("a", "b", "c")
            log("safeJoinToString: ${list.safeJoinToString("|")}")
            list.ifNotEmpty { log("ifNotEmpty: size=${it.size}") }

            val nullList: List<String>? = null
            log("null safeJoinToString: '${nullList.safeJoinToString()}'")
        })

        @Suppress("DEPRECATION")
        container.addView(button("AwLog") {
            AwLog.init(isDebug = true, prefix = "Demo")
            AwLog.d("Demo", "Debug log from aw-utils demo")
            AwLog.i("Demo", "Info log from aw-utils demo")
            AwLog.w("Demo", "Warn log from aw-utils demo")
            AwLog.e("Demo", "Error log from aw-utils demo")
            log("Logs printed to Logcat (prefix: Demo)")
        })
    }

    private fun button(text: String, onClick: () -> Unit): Button {
        return Button(this).apply {
            this.text = text
            onClick(interval = 300L) { onClick() }
        }
    }

    private fun log(msg: String) {
        tvLog.append("$msg\n")
    }

    private fun Long.friendlySize(): String {
        val size = this.toDouble()
        return when {
            size < 1024 -> "%.0f B".format(size)
            size < 1024 * 1024 -> "%.1f KB".format(size / 1024)
            size < 1024 * 1024 * 1024 -> "%.1f MB".format(size / (1024 * 1024))
            else -> "%.2f GB".format(size / (1024 * 1024 * 1024))
        }
    }
}
