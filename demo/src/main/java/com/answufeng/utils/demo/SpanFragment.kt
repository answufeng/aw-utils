package com.answufeng.utils.demo

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.answufeng.utils.*

class SpanFragment : BaseDemoFragment() {

    override fun setupDemo() {
        addTitle("🎨 单一样式")

        val colorTv = TextView(requireContext()).apply {
            text = "红色文字".spanColor(Color.RED)
            textSize = 16f
            setPadding(0, 8, 0, 4)
        }
        container.addView(colorTv)

        val boldTv = TextView(requireContext()).apply {
            text = "加粗文字".spanBold()
            textSize = 16f
            setPadding(0, 4, 0, 4)
        }
        container.addView(boldTv)

        val underlineTv = TextView(requireContext()).apply {
            text = "下划线文字".spanUnderline()
            textSize = 16f
            setPadding(0, 4, 0, 4)
        }
        container.addView(underlineTv)

        val strikeTv = TextView(requireContext()).apply {
            text = "删除线文字".spanStrikethrough()
            textSize = 16f
            setPadding(0, 4, 0, 4)
        }
        container.addView(strikeTv)

        addTitle("🎭 组合样式 (DSL)")
        val combinedTv = TextView(requireContext()).apply {
            text = spannable {
                append("价格：".spanBold())
                append("¥99".spanColor(Color.RED))
                append(" ¥199".spanStrikethrough())
            }
            textSize = 16f
            setPadding(0, 8, 0, 4)
        }
        container.addView(combinedTv)

        addTitle("👆 可点击 Span")
        val clickableTv = TextView(requireContext()).apply {
            enableClickableSpan()
            text = "查看用户协议和隐私政策".buildClickableSpans(
                intArrayOf(2, 6, 7, 11),
                arrayOf({ addLog("点击了「用户协议」") }, { addLog("点击了「隐私政策」") })
            )
            textSize = 16f
            setPadding(0, 8, 0, 4)
        }
        container.addView(clickableTv)

        addTitle("🔤 字体大小")
        val sizeTv = TextView(requireContext()).apply {
            text = "大字小字混合".spanSize(32, 0, 2).let {
                SpannableString(it).also { ss ->
                    ss.setSpan(
                        android.text.style.AbsoluteSizeSpan(14),
                        2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            textSize = 16f
            setPadding(0, 8, 0, 4)
        }
        container.addView(sizeTv)

        addTitle("🎨 颜色扩展")
        val color = randomColor()
        addLog("随机颜色: ${color.toHexColor()}")
        addLog("无透明度: ${color.toHexColorNoAlpha()}")
        addLog("半透明: ${color.withAlpha(128).toHexColor()}")
        val blended = Color.RED.blend(Color.BLUE, 0.5f)
        addLog("红蓝混合: ${blended.toHexColor()}")
    }

    private fun String.buildClickableSpans(
        ranges: IntArray,
        callbacks: Array<() -> Unit>
    ): SpannableString {
        return SpannableString(this).also { ss ->
            for (i in callbacks.indices) {
                val start = ranges[i * 2]
                val end = ranges[i * 2 + 1]
                ss.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) { callbacks[i]() }
                    override fun updateDrawState(ds: android.text.TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = Color.parseColor("#2196F3")
                        ds.isUnderlineText = true
                    }
                }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }
}
