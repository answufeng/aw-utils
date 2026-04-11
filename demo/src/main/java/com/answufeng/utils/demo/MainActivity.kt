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

            log("md5: ${"hello".md5()}")
            log("sha256: ${"hello".sha256().take(16)}...")
        })

        container.addView(button("Date Extensions") {
            val now = System.currentTimeMillis()
            log("formatDate: ${now.formatDate()}")
            log("isToday: ${now.isToday()}")
            log("toFriendlyTime: ${now.toFriendlyTime()}")
        })

        container.addView(button("Device Info") {
            log("Brand: $deviceBrand")
            log("Model: $deviceModel")
            log("OS: $osVersion")
            log("SDK: $sdkVersion")
            log("Summary: ${deviceSummary()}")
        })

        container.addView(button("Network Check") {
            log("Available: ${isNetworkAvailable()}")
            log("WiFi: ${isWifiConnected()}")
            log("Type: ${getNetworkTypeName()}")
        })

        container.addView(button("Context Extensions") {
            log("screenWidth: $screenWidth px")
            log("screenHeight: $screenHeight px")
            log("statusBarHeight: $statusBarHeight px")
            log("100dp = ${100f.dp}px")
        })

        container.addView(button("Encode Extensions") {
            val base64 = "hello".encodeBase64()
            log("Base64 encode: $base64")
            log("Base64 decode: ${base64.decodeBase64String()}")

            val hex = byteArrayOf(0x01, 0x02, 0xFF).toHexString()
            log("Hex: $hex")
        })

        container.addView(button("System Extensions") {
            copyToClipboard("Copied text from aw-utils demo")
            log("Copied to clipboard")

            log("Version: ${appVersionName()}")
            toast("Hello from aw-utils!")
        })
    }

    private fun button(text: String, onClick: () -> Unit): Button {
        return Button(this).apply { this.text = text; setOnClickListener { onClick() } }
    }

    private fun log(msg: String) { tvLog.append("$msg\n") }
}
