package com.answufeng.utils.demo

import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.answufeng.utils.*

class MoreFragment : BaseDemoFragment() {

    override fun setupDemo() {
        val ctx = requireContext()

        addTitle("📝 EditText 扩展")
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

        addTitle("🔑 SharedPreferences")
        val spBtn = Button(ctx).apply {
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

        addTitle("📋 AwLog 日志")
        val logBtn = Button(ctx).apply {
            text = "测试 AwLog 输出"
            @Suppress("DEPRECATION")
            debounceClick {
                AwLog.init(isDebug = true, prefix = "Demo")
                AwLog.d("MoreFragment", "这是一条 Debug 日志")
                AwLog.e("MoreFragment", "这是一条 Error 日志")
                addLog("AwLog 已输出到 Logcat (tag: Demo-MoreFragment)")
            }
        }
        container.addView(logBtn)

        addTitle("🔗 Activity 扩展")
        addLog("startActivity<DetailActivity>(bundleOf(\"id\" to 42))")
        addLog("extraOrNull<String>(\"user_id\")")
        addLog("argumentOrNull<Int>(\"count\")")

        addTitle("🖼️ ImageView 扩展")
        addLog("setTint(color) — 设置 tint 颜色")
        addLog("clearTint() — 清除 tint")
        addLog("setImageWithTint(resId, color) — 设置图片+tint")

        addTitle("📝 TextView 扩展")
        addLog("setDrawableStart(drawable) — 设置左侧 drawable")
        addLog("setDrawableTint(color) — 设置 drawable tint")
        addLog("clearDrawables() — 清除所有 drawable")

        addTitle("🖼️ Bitmap 扩展")
        addLog("toCircle() — 裁剪为圆形")
        addLog("toRounded(radius) — 裁剪为圆角")
        addLog("scaleMaxSize(maxSize) — 按比例缩放")
        addLog("compressTo(file) — 压缩保存到文件")

        addTitle("🔗 Uri 扩展")
        addLog("toFilePath(context) — content:// 转 file path")
        addLog("getFileName(context) — 获取文件名")
        addLog("getMimeType(context) — 获取 MIME 类型")
    }
}

@Suppress("DEPRECATION")
object DemoPrefs : SpDelegate("demo_prefs") {
    var clickCount by int("click_count", 0)
}
