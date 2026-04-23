package com.answufeng.utils.demo

import android.media.AudioManager
import com.answufeng.utils.*

class DeviceFragment : BaseDemoFragment() {

    @OptIn(AwExperimentalApi::class)
    override fun setupDemo() {
        val ctx = requireContext()

        addTitle("设备信息")
        addLog("品牌: ${ctx.deviceBrand}")
        addLog("型号: ${ctx.deviceModel}")
        addLog("厂商: ${ctx.deviceManufacturer}")
        addLog("系统版本: ${ctx.osVersion}")
        addLog("SDK版本: ${ctx.sdkVersion}")
        addLog("设备摘要: ${ctx.deviceSummary()}")

        addTitle("厂商启发式（Rom）")
        addLog("Xiaomi: ${Rom.isXiaomi()} | Huawei: ${Rom.isHuawei()} | Honor: ${Rom.isHonor()}")
        addLog("OPPO: ${Rom.isOppo()} | vivo: ${Rom.isVivo()} | Samsung: ${Rom.isSamsung()}")

        addTitle("媒体音量（VolumeExt，只读展示）")
        addLog("STREAM_MUSIC: ${ctx.getStreamVolume(AudioManager.STREAM_MUSIC)} / ${ctx.getStreamMaxVolume(AudioManager.STREAM_MUSIC)}")

        addTitle("屏幕信息")
        addLog("是否平板: ${ctx.isTablet}")
        addLog("是否横屏: ${ctx.isLandscape}")
        addLog("是否竖屏: ${ctx.isPortrait}")
        addLog("屏幕宽度: ${ctx.screenWidth} px")
        addLog("屏幕高度: ${ctx.screenHeight} px")
        addLog("状态栏高度: ${ctx.statusBarHeight} px")
        addLog("导航栏高度: ${ctx.navigationBarHeight} px")

        addTitle("应用信息")
        addLog("是否Debug: ${ctx.isAppDebug()}")
        addLog("是否前台: ${ctx.isAppForeground()}") // @Deprecated, 仅作演示
        addLog("是否系统应用: ${ctx.isSystemApp(ctx.packageName)}")
        addLog("已安装微信: ${ctx.isAppInstalled("com.tencent.mm")}")
        addLog("应用名: ${ctx.getAppName(ctx.packageName)}")
        addLog("签名SHA1: ${ctx.getAppSignatureSHA1().take(16)}...")
        addLog("版本名: ${ctx.appVersionName()}")
        addLog("版本号: ${ctx.appVersionCode()}")

        addTitle("尺寸转换")
        addLog("100dp = ${100.dpToPx(ctx)}px")
        addLog("14sp = ${14.spToPx(ctx)}px")
        addLog("48px = ${48.pxToDp(ctx)}dp")
    }
}
