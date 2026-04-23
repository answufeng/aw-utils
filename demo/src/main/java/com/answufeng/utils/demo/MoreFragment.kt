package com.answufeng.utils.demo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.widget.EditText
import android.widget.LinearLayout
import android.util.Log
import com.answufeng.utils.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import java.io.File

class MoreFragment : BaseDemoFragment() {

    private var removeClipboardListener: (() -> Unit)? = null

    override fun onDestroyView() {
        removeClipboardListener?.invoke()
        removeClipboardListener = null
        super.onDestroyView()
    }

    override fun setupDemo() {
        val ctx = requireContext()

        addTitle("EditText 扩展")
        val editText = EditText(ctx).apply {
            hint = "输入文本观察变化"
            setMaxLength(50)
            onTextChanged { text ->
                addLog("文本变化: \"$text\" (${text.length}/50)")
            }
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        container.addView(editText)

        addTitle("SharedPreferences")
        val spBtn = MaterialButton(ctx).apply {
            text = "测试 SpDelegate 读写"
            @Suppress("DEPRECATION")
            debounceClick {
                val prefs = DemoPrefs
                prefs.init(ctx.applicationContext)
                prefs.clickCount = (prefs.clickCount ?: 0) + 1
                addLog("SpDelegate 点击次数: ${prefs.clickCount}")
            }
        }
        container.addView(spBtn)

        addTitle("Log 日志")
        val logBtn = MaterialButton(ctx).apply {
            text = "测试 Log 输出"
            @Suppress("DEPRECATION")
            debounceClick {
                Log.d("Demo-MoreFragment", "这是一条 Debug 日志")
                Log.e("Demo-MoreFragment", "这是一条 Error 日志")
                addLog("Log 已输出到 Logcat (tag: Demo-MoreFragment)")
            }
        }
        container.addView(logBtn)

        addTitle("Activity 扩展")
        addLog("startActivity<DetailActivity>(bundleOf(\"id\" to 42))")
        addLog("extraOrNull<String>(\"user_id\")")
        addLog("argumentOrNull<Int>(\"count\")")

        addTitle("ImageView 扩展")
        addLog("setTint(color) — 设置 tint 颜色")
        addLog("clearTint() — 清除 tint")
        addLog("setImageWithTint(resId, color) — 设置图片+tint")

        addTitle("TextView 扩展")
        addLog("setDrawableStart(drawable) — 设置左侧 drawable")
        addLog("setDrawableTint(color) — 设置 drawable tint")
        addLog("clearDrawables() — 清除所有 drawable")

        addTitle("Bitmap 扩展")
        addLog("toCircle() — 裁剪为圆形")
        addLog("toRounded(radius) — 裁剪为圆角")
        addLog("scaleMaxSize(maxSize) — 按比例缩放")
        addLog("compressTo(file) — 压缩保存到文件")

        addTitle("Uri 扩展")
        addLog("toFilePath(context) — content:// 转 file path")
        addLog("getFileName(context) — 获取文件名")
        addLog("getMimeType(context) — 获取 MIME 类型")

        addTitle("Manifest meta-data（MetaDataExt）")
        addLog("application: ${ctx.getApplicationMetaDataString("demo_meta_app")}")
        addLog("activity: ${requireActivity().getActivityMetaDataString("demo_meta_activity")}")

        addTitle("剪贴板监听（SystemExt）")
        val clipToggle = MaterialButton(ctx).apply {
            text = "切换 主剪贴板 变化监听"
            debounceClick {
                val existing = removeClipboardListener
                if (existing != null) {
                    existing()
                    removeClipboardListener = null
                    addLog("onPrimaryClipChanged 已取消")
                } else {
                    removeClipboardListener = ctx.onPrimaryClipChanged {
                        if (!isAdded) return@onPrimaryClipChanged
                        requireActivity().runOnUiThread {
                            if (isAdded) addLog("剪贴板主内容已变化")
                        }
                    }
                    addLog("已注册 onPrimaryClipChanged（可复制文本试）")
                }
            }
        }
        container.addView(clipToggle)

        addTitle("存储路径（StoragePathExt）")
        addLog("internalCacheDir: ${ctx.internalCacheDir.absolutePath}")
        addLog("internalFilesDir: ${ctx.internalFilesDir.absolutePath}")
        addLog("externalAppCacheDir: ${ctx.externalAppCacheDir?.absolutePath ?: "null"}")

        addTitle("手电筒（FlashlightExt）")
        addLog("isTorchSupported: ${ctx.isTorchSupported()}")
        val torchRow = LinearLayout(ctx).apply { orientation = LinearLayout.HORIZONTAL }
        val torchOn = MaterialButton(ctx).apply {
            text = "手电筒 ON"
            debounceClick { addLog("setTorchEnabled(true): ${ctx.setTorchEnabled(true)}") }
        }
        val torchOff = MaterialButton(ctx).apply {
            text = "手电筒 OFF"
            debounceClick { addLog("setTorchEnabled(false): ${ctx.setTorchEnabled(false)}") }
        }
        torchRow.addView(
            torchOn,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f),
        )
        torchRow.addView(
            torchOff,
            LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f),
        )
        container.addView(torchRow)

        addTitle("系统设置（IntentExt）")
        val wirelessBtn = MaterialButton(ctx).apply {
            text = "openWirelessSettings()"
            debounceClick { addLog("openWirelessSettings: ${ctx.openWirelessSettings()}") }
        }
        container.addView(wirelessBtn)

        addTitle("Assets / Raw（AssetsRawExt）")
        val assetBtn = MaterialButton(ctx).apply {
            text = "读取 assets/sample_asset.txt"
            debounceClick {
                runCatching { ctx.readAssetText("sample_asset.txt") }
                    .onSuccess { addLog("assets: $it") }
                    .onFailure { addLog("assets 失败: ${it.message}") }
            }
        }
        container.addView(assetBtn)
        val rawBtn = MaterialButton(ctx).apply {
            text = "读取 res/raw/sample_raw.txt"
            debounceClick {
                runCatching { ctx.readRawText(R.raw.sample_raw) }
                    .onSuccess { addLog("raw: $it") }
                    .onFailure { addLog("raw 失败: ${it.message}") }
            }
        }
        container.addView(rawBtn)
        val copyAssetBtn = MaterialButton(ctx).apply {
            text = "copyAssetToFile → filesDir/asset_copy.txt"
            debounceClick {
                val dest = File(ctx.filesDir, "asset_copy.txt")
                val ok = ctx.copyAssetToFile("sample_asset.txt", dest)
                addLog("copyAssetToFile: $ok → ${dest.readTextOrNull()?.take(40)}")
            }
        }
        container.addView(copyAssetBtn)

        addTitle("通知（NotificationExt）")
        val notifBtn = MaterialButton(ctx).apply {
            text = "showSimpleNotification（需 Android 13+ 通知权限）"
            debounceClick {
                if (Build.VERSION.SDK_INT >= 33 &&
                    ContextCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
                    addLog("已请求 POST_NOTIFICATIONS，授权后请再点一次")
                    return@debounceClick
                }
                if (!ctx.areNotificationsEnabled()) {
                    Snackbar.make(requireView(), "系统设置中已关闭通知", Snackbar.LENGTH_LONG).show()
                    addLog("areNotificationsEnabled() == false")
                    return@debounceClick
                }
                ctx.showSimpleNotification(
                    channelId = "aw_utils_demo",
                    notificationId = 42,
                    title = "aw-utils Demo",
                    text = "简单通知示例",
                    smallIcon = android.R.drawable.ic_dialog_info,
                    channelName = "Demo",
                )
                addLog("showSimpleNotification 已调用")
            }
        }
        container.addView(notifBtn)

        addTitle("清理目录子项（CleanExt）")
        val clearCacheBtn = MaterialButton(ctx).apply {
            text = "clearInternalCacheChildren()"
            debounceClick {
                val ok = ctx.clearInternalCacheChildren()
                addLog("clearInternalCacheChildren: $ok")
            }
        }
        container.addView(clearCacheBtn)
        val clearFilesBtn = MaterialButton(ctx).apply {
            text = "clearInternalFilesChildren()"
            debounceClick { addLog("clearInternalFilesChildren: ${ctx.clearInternalFilesChildren()}") }
        }
        container.addView(clearFilesBtn)
        val clearExtCacheBtn = MaterialButton(ctx).apply {
            text = "clearExternalCacheChildren()"
            debounceClick { addLog("clearExternalCacheChildren: ${ctx.clearExternalCacheChildren()}") }
        }
        container.addView(clearExtCacheBtn)

        addTitle("Zip（ZipExt）")
        val zipBtn = MaterialButton(ctx).apply {
            text = "将 filesDir/demo_zip 打成 zip 并解压到 cache"
            debounceClick {
                val src = File(ctx.filesDir, "demo_zip").apply { mkdirs() }
                File(src, "hello.txt").writeText("zip demo ${System.currentTimeMillis()}")
                val zipOut = File(ctx.cacheDir, "demo_more.zip")
                val okZip = src.zipDirectoryTo(zipOut)
                val dest = File(ctx.cacheDir, "demo_unzip").apply { mkdirs() }
                val okUnzip = zipOut.unzipToDirectory(dest)
                addLog("zip: $okZip, unzip: $okUnzip → ${File(dest, "hello.txt").readTextOrNull()}")
            }
        }
        container.addView(zipBtn)

        addTitle("状态栏（BarExt）")
        val barLightBtn = MaterialButton(ctx).apply {
            text = "浅色状态栏背景 + 深色图标"
            debounceClick {
                requireActivity().setStatusBarColorAndStyle(Color.WHITE, darkStatusBarIcons = true)
                addLog("setStatusBarColorAndStyle(白底, darkIcons=true)")
            }
        }
        container.addView(barLightBtn)
        val barDarkBtn = MaterialButton(ctx).apply {
            text = "深色状态栏背景 + 浅色图标"
            debounceClick {
                requireActivity().setStatusBarColorAndStyle(Color.BLACK, darkStatusBarIcons = false)
                addLog("setStatusBarColorAndStyle(黑底, darkIcons=false)")
            }
        }
        container.addView(barDarkBtn)
        val navLightBtn = MaterialButton(ctx).apply {
            text = "浅色导航栏 + 深色按钮"
            debounceClick {
                requireActivity().setNavigationBarColorAndStyle(Color.WHITE, darkNavigationBarIcons = true)
                addLog("setNavigationBarColorAndStyle(白底, darkIcons=true)")
            }
        }
        container.addView(navLightBtn)
        val navDarkBtn = MaterialButton(ctx).apply {
            text = "深色导航栏 + 浅色按钮"
            debounceClick {
                requireActivity().setNavigationBarColorAndStyle(Color.BLACK, darkNavigationBarIcons = false)
                addLog("setNavigationBarColorAndStyle(黑底, darkIcons=false)")
            }
        }
        container.addView(navDarkBtn)
    }
}

@Suppress("DEPRECATION")
object DemoPrefs : SpDelegate("demo_prefs") {
    var clickCount by int("click_count", 0)
}
