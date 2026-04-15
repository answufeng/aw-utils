package com.answufeng.utils.demo

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.answufeng.utils.*
import com.google.android.material.card.MaterialCardView

@OptIn(AwExperimentalApi::class)
class MainActivity : AppCompatActivity() {

    private lateinit var tvLog: TextView
    private lateinit var logScrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 主布局
        val mainLayout = findViewById<LinearLayout>(R.id.mainLayout)

        // 标题
        mainLayout.addView(TextView(this).apply {
            text = "🛠️ aw-utils 功能演示"
            textSize = 20f
            setPadding(0, 0, 0, 20)
        })

        // 文本处理卡片
        val textCard = createCard("文本处理")
        val textLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        textLayout.addView(createButton("📝 字符串扩展", ::testStringExtensions))
        textLayout.addView(createButton("🔍 正则扩展", ::testRegexExtensions))
        textLayout.addView(createButton("🎨 富文本扩展", ::testSpanExtensions))
        textCard.addView(textLayout)
        mainLayout.addView(textCard)

        // 日期时间卡片
        val dateCard = createCard("日期时间")
        val dateLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        dateLayout.addView(createButton("📅 日期扩展", ::testDateExtensions))
        dateCard.addView(dateLayout)
        mainLayout.addView(dateCard)

        // 安全加密卡片
        val securityCard = createCard("安全加密")
        val securityLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        securityLayout.addView(createButton("🔒 编码扩展", ::testEncodeExtensions))
        securityCard.addView(securityLayout)
        mainLayout.addView(securityCard)

        // 设备信息卡片
        val deviceCard = createCard("设备信息")
        val deviceLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        deviceLayout.addView(createButton("📱 设备信息", ::showDeviceInfo))
        deviceLayout.addView(createButton("🖥️ 屏幕扩展", ::testScreenExtensions))
        deviceLayout.addView(createButton("📳 振动扩展", ::testVibrateExtensions))
        deviceCard.addView(deviceLayout)
        mainLayout.addView(deviceCard)

        // 应用功能卡片
        val appCard = createCard("应用功能")
        val appLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        appLayout.addView(createButton("📦 应用扩展", ::testAppExtensions))
        appLayout.addView(createButton("📶 网络状态", ::checkNetwork))
        appLayout.addView(createButton("🔗 意图扩展", ::testIntentExtensions))
        appLayout.addView(createButton("⚙️ 系统扩展", ::testSystemExtensions))
        appCard.addView(appLayout)
        mainLayout.addView(appCard)

        // 工具功能卡片
        val utilsCard = createCard("工具功能")
        val utilsLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        utilsLayout.addView(createButton("📁 文件扩展", ::testFileExtensions))
        utilsLayout.addView(createButton("🎲 随机扩展", ::testRandomExtensions))
        utilsLayout.addView(createButton("📏 上下文扩展", ::testContextExtensions))
        utilsLayout.addView(createButton("📋 集合扩展", ::testCollectionExtensions))
        utilsLayout.addView(createButton("🔄 进程扩展", ::testProcessExtensions))
        utilsCard.addView(utilsLayout)
        mainLayout.addView(utilsCard)

        // 管理功能卡片
        val manageCard = createCard("管理功能")
        val manageLayout = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        manageLayout.addView(createButton("🗑️ 清除日志", ::clearLog))
        manageCard.addView(manageLayout)
        mainLayout.addView(manageCard)

        // 日志区域
        mainLayout.addView(TextView(this).apply {
            text = "操作日志："
            textSize = 16f
            setPadding(0, 20, 0, 10)
        })

        logScrollView = findViewById(R.id.logScrollView)
        tvLog = findViewById(R.id.tvLog)

        // 显示初始信息
        log("✅ 工具库初始化完成")
        log("📊 点击按钮测试各项功能")
    }

    private fun createCard(title: String): MaterialCardView {
        return MaterialCardView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 16)
            }
            setPadding(20, 20, 20, 20)

            addView(TextView(this@MainActivity).apply {
                text = title
                textSize = 16f
                setPadding(0, 0, 0, 12)
            })
        }
    }

    private fun createButton(text: String, onClick: () -> Unit): Button {
        return Button(this).apply {
            this.text = text
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 4, 0, 4)
            }
            setOnClickListener { onClick() }
        }
    }

    private fun log(msg: String) {
        tvLog.append("$msg\n")
        logScrollView.post { logScrollView.fullScroll(ScrollView.FOCUS_DOWN) }
        android.util.Log.d("AwUtilsDemo", msg)
    }

    private fun clearLog() {
        tvLog.text = "日志已清除\n"
    }

    private fun testStringExtensions() {
        log("📝 字符串扩展:")
        log("  手机号校验: ${
            "13812345678".isPhoneNumber()
        }")
        log("  手机号掩码: ${
            "13812345678".maskPhone()
        }")
        log("  邮箱校验: ${
            "test@example.com".isEmail()
        }")
        log("  身份证校验: ${
            "110101199001011234".isIdCard()
        }")
        log("  MD5: ${
            "hello".md5()
        }")
        log("  SHA256: ${
            "hello".sha256().take(16)
        }...")
        log("  截断: ${
            "Hello World".truncate(5)
        }")
    }

    private fun testRegexExtensions() {
        log("🔍 正则扩展:")
        log("  IP校验: ${
            "192.168.1.1".isIP()
        }")
        log("  中文校验: ${
            "你好世界".isChinese()
        }")
        log("  用户名校验: ${
            "user123".isUsername()
        }")
        log("  强密码: ${
            "Abc@1234".isStrongPassword()
        }")
        log("  中等密码: ${
            "abc123".isMediumPassword()
        }")
        log("  密码强度: ${
            "Abc@1234".passwordStrength()
        }")
        log("  车牌号: ${
            "京A12345".isCarPlate()
        }")
        log("  MAC地址: ${
            "00:1A:2B:3C:4D:5E".isMacAddress()
        }")
    }

    private fun testDateExtensions() {
        log("📅 日期扩展:")
        val now = System.currentTimeMillis()
        log("  格式化: ${now.formatDate()}")
        log("  自定义格式: ${now.formatDate("yyyy/MM/dd")}")
        log("  是否今天: ${now.isToday()}")
        log("  友好时间(5分钟前): ${(now - 5 * 60_000).toFriendlyTime()}")
        log("  友好时间(3小时前): ${(now - 3 * 3_600_000).toFriendlyTime()}")
    }

    private fun testRandomExtensions() {
        log("🎲 随机扩展:")
        log("  随机字符串(8位): ${randomString(8)}")
        log("  随机数字(6位): ${randomNumericString(6)}")
        log("  随机字母(10位): ${randomLetterString(10)}")
        log("  随机整数(1-100): ${randomInt(1, 100)}")
        log("  随机颜色: #${Integer.toHexString(randomColor()).uppercase()}")
        log("  列表随机元素: ${listOf("🍎", "🍌", "🍊", "🍇").randomElement()}")
    }

    private fun testFileExtensions() {
        log("📁 文件扩展:")
        val tempFile = cacheDir.resolve("test.txt").apply { writeText("hello aw-utils") }
        log("  文件大小: ${tempFile.friendlySize()}")
        log("  扩展名: ${tempFile.extensionName}")
        log("  MD5: ${tempFile.md5()}")
        log("  缓存目录大小: ${cacheDir.totalSize().toFriendlySize()}")
    }

    private fun testEncodeExtensions() {
        log("🔒 编码扩展:")
        val base64 = "hello".encodeBase64()
        log("  Base64编码: $base64")
        log("  Base64解码: ${base64.decodeBase64ToString()}")
        val hex = byteArrayOf(0x0A, 0xFF.toByte()).toHexString()
        log("  Hex编码: $hex")
        log("  Hex解码: ${hex.hexToByteArray().toList()}")
    }

    private fun showDeviceInfo() {
        log("📱 设备信息:")
        log("  品牌: $deviceBrand")
        log("  型号: $deviceModel")
        log("  厂商: $deviceManufacturer")
        log("  系统版本: $osVersion")
        log("  SDK版本: $sdkVersion")
        log("  设备摘要: ${deviceSummary()}")
    }

    private fun testScreenExtensions() {
        log("🖥️ 屏幕扩展:")
        log("  是否平板: $isTablet")
        log("  是否横屏: $isLandscape")
        log("  是否竖屏: $isPortrait")
        log("  窗口亮度: ${getWindowBrightness()}")
        log("  屏幕常亮: ${isScreenKeepOn()}")
    }

    private fun testAppExtensions() {
        log("📦 应用扩展:")
        log("  是否Debug: ${isAppDebug()}")
        log("  是否前台: ${isAppForeground()}")
        log("  是否系统应用: ${isSystemApp(packageName)}")
        log("  已安装微信: ${isAppInstalled("com.tencent.mm")}")
        log("  应用名: ${getAppName(packageName)}")
        log("  签名SHA1: ${getAppSignatureSHA1().take(16)}...")
    }

    private fun checkNetwork() {
        log("📶 网络状态:")
        log("  网络可用: ${isNetworkAvailable()}")
        log("  WiFi连接: ${isWifiConnected()}")
        log("  网络类型: ${getNetworkType()}")
    }

    private fun testIntentExtensions() {
        log("🔗 意图扩展(仅展示，不实际跳转):")
        log("  sendEmail → mailto:xxx@example.com")
        log("  sendSMS → smsto:10086")
        log("  openMap → geo:39.9,116.4")
        log("  openAppMarket → market://details?id=...")
        log("  openWifiSettings → ACTION_WIFI_SETTINGS")
        log("  installApk → ACTION_VIEW apk")
    }

    private fun testContextExtensions() {
        log("📏 上下文扩展:")
        log("  屏幕宽度: $screenWidth px")
        log("  屏幕高度: $screenHeight px")
        log("  状态栏高度: $statusBarHeight px")
        log("  100dp = ${100.dp}px")
        log("  14sp = ${14.sp}px")
        log("  100dpToPx = ${100.dpToPx(this@MainActivity)}px")
    }

    private fun testSystemExtensions() {
        log("⚙️ 系统扩展:")
        copyToClipboard("从 aw-utils demo 复制的文本")
        log("  已复制到剪贴板")
        log("  应用版本: ${appVersionName()}")
        log("  版本号: ${appVersionCode()}")
        log("  相机权限: ${hasPermission(android.Manifest.permission.CAMERA)}")
        toast("Hello from aw-utils!")
    }

    private fun testCollectionExtensions() {
        log("📋 集合扩展:")
        val list = listOf("a", "b", "c")
        log("  安全连接: ${list.safeJoinToString("|")}")
        list.ifNotEmpty { log("  非空回调: size=${it.size}") }
        val nullList: List<String>? = null
        log("  空集合安全连接: '${nullList.safeJoinToString()}'")
    }

    private fun testSpanExtensions() {
        log("🎨 富文本扩展:")
        val colored = "Hello World".spanColor(Color.RED, 0, 5)
        log("  颜色Span: $colored")
        val bold = "Hello World".spanBold(0, 5)
        log("  加粗Span: $bold")
        val underline = "Hello World".spanUnderline(6, 11)
        log("  下划线Span: $underline")
        val strike = "Hello World".spanStrikethrough(0, 5)
        log("  删除线Span: $strike")
        val combined = spannable {
            append("Hello ".spanBold())
            append("World".spanColor(Color.BLUE))
        }
        log("  组合Span: $combined")
    }

    private fun testVibrateExtensions() {
        log("📳 振动扩展:")
        vibrate()
        log("  已触发短振动(50ms)")
    }

    private fun testProcessExtensions() {
        log("🔄 进程扩展:")
        log("  是否主线程: $isMainThread")
        runOnUiThread {
            log("  runOnUiThread 执行成功 ✓")
        }
    }
}
