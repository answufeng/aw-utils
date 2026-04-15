package com.answufeng.utils.demo

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.answufeng.utils.*

@OptIn(AwExperimentalApi::class)
class MainActivity : AppCompatActivity() {

    private lateinit var tvLog: TextView
    private lateinit var logScrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLog = findViewById(R.id.tvLog)
        logScrollView = findViewById(R.id.logScrollView)

        findViewById<Button>(R.id.btnString).debounceClick(300L) { testStringExtensions() }
        findViewById<Button>(R.id.btnRegex).debounceClick(300L) { testRegexExtensions() }
        findViewById<Button>(R.id.btnDate).debounceClick(300L) { testDateExtensions() }
        findViewById<Button>(R.id.btnRandom).debounceClick(300L) { testRandomExtensions() }
        findViewById<Button>(R.id.btnFile).debounceClick(300L) { testFileExtensions() }
        findViewById<Button>(R.id.btnEncode).debounceClick(300L) { testEncodeExtensions() }
        findViewById<Button>(R.id.btnDevice).debounceClick(300L) { showDeviceInfo() }
        findViewById<Button>(R.id.btnScreen).debounceClick(300L) { testScreenExtensions() }
        findViewById<Button>(R.id.btnApp).debounceClick(300L) { testAppExtensions() }
        findViewById<Button>(R.id.btnNetwork).debounceClick(300L) { checkNetwork() }
        findViewById<Button>(R.id.btnIntent).debounceClick(300L) { testIntentExtensions() }
        findViewById<Button>(R.id.btnContext).debounceClick(300L) { testContextExtensions() }
        findViewById<Button>(R.id.btnSystem).debounceClick(300L) { testSystemExtensions() }
        findViewById<Button>(R.id.btnCollection).debounceClick(300L) { testCollectionExtensions() }
        findViewById<Button>(R.id.btnSpan).debounceClick(300L) { testSpanExtensions() }
        findViewById<Button>(R.id.btnVibrate).debounceClick(300L) { testVibrateExtensions() }
        findViewById<Button>(R.id.btnProcess).debounceClick(300L) { testProcessExtensions() }
        findViewById<Button>(R.id.btnClearLog).debounceClick(300L) { clearLog() }

        log("✅ 工具库初始化完成，共 ${"abcdefghijklmnopqrstuvwxyz".length} 个模块")
    }

    private fun log(msg: String) {
        tvLog.append("$msg\n")
        logScrollView.post { logScrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    private fun clearLog() {
        tvLog.text = "日志已清除\n"
    }

    private fun testStringExtensions() {
        log("📝 字符串扩展:")
        log("  手机号校验: ${"13812345678".isPhoneNumber()}")
        log("  手机号掩码: ${"13812345678".maskPhone()}")
        log("  邮箱校验: ${"test@example.com".isEmail()}")
        log("  身份证校验: ${"110101199001011234".isIdCard()}")
        log("  MD5: ${"hello".md5()}")
        log("  SHA256: ${"hello".sha256().take(16)}...")
        log("  截断: ${"Hello World".truncate(5)}")
    }

    private fun testRegexExtensions() {
        log("🔍 正则扩展:")
        log("  IP校验: ${"192.168.1.1".isIP()}")
        log("  中文校验: ${"你好世界".isChinese()}")
        log("  用户名校验: ${"user123".isUsername()}")
        log("  强密码: ${"Abc@1234".isStrongPassword()}")
        log("  中等密码: ${"abc123".isMediumPassword()}")
        log("  密码强度: ${"Abc@1234".passwordStrength()}")
        log("  车牌号: ${"京A12345".isCarPlate()}")
        log("  MAC地址: ${"00:1A:2B:3C:4D:5E".isMacAddress()}")
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
