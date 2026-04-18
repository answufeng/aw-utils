package com.answufeng.utils.demo

import com.answufeng.utils.*

class NetworkFragment : BaseDemoFragment() {

    override fun setupDemo() {
        val ctx = requireContext()

        addTitle("📶 网络状态")
        addLog("网络可用: ${ctx.isNetworkAvailable()}")
        addLog("WiFi连接: ${ctx.isWifiConnected()}")
        addLog("移动数据: ${ctx.isMobileDataConnected()}")
        addLog("网络类型: ${ctx.getNetworkType()}")

        addTitle("🔗 意图扩展（仅展示）")
        addLog("sendEmail → mailto:xxx@example.com")
        addLog("sendSMS → smsto:10086")
        addLog("openMap → geo:39.9,116.4")
        addLog("openAppMarket → market://details?id=...")
        addLog("openWifiSettings → ACTION_WIFI_SETTINGS")

        addTitle("⚙️ 系统扩展")
        ctx.copyToClipboard("从 aw-utils demo 复制的文本")
        addLog("已复制到剪贴板")
        addLog("相机权限: ${ctx.hasPermission(android.Manifest.permission.CAMERA)}")

        addTitle("🔄 进程扩展")
        addLog("是否主线程: $isMainThread")
        runOnUiThread {
            addLog("runOnUiThread 执行成功 ✓")
        }
    }
}
