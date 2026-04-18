package com.answufeng.utils.demo

import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.answufeng.utils.*

class ViewFragment : BaseDemoFragment() {

    override fun setupDemo() {
        val ctx = requireContext()

        addTitle("👆 防抖点击")
        val debounceBtn = Button(ctx).apply {
            text = "防抖按钮 (500ms)"
            debounceClick { 
                addLog("防抖点击触发 ✓")
            }
        }
        container.addView(debounceBtn)

        addTitle("👁️ 可见性控制")
        val targetTv = TextView(ctx).apply {
            text = "我是目标 View"
            textSize = 14f
            setPadding(0, 8, 0, 8)
        }
        container.addView(targetTv)

        val visibleBtn = Button(ctx).apply {
            text = "VISIBLE"
            debounceClick { targetTv.visible() }
        }
        val goneBtn = Button(ctx).apply {
            text = "GONE"
            debounceClick { targetTv.gone() }
        }
        val toggleBtn = Button(ctx).apply {
            text = "TOGGLE"
            debounceClick { targetTv.toggleVisibility() }
        }
        val row = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            addView(visibleBtn, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
            addView(goneBtn, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
            addView(toggleBtn, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        }
        container.addView(row)

        addTitle("⌨️ 键盘")
        val editText = EditText(ctx).apply {
            hint = "点击后弹出键盘"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        container.addView(editText)

        val showKeyBtn = Button(ctx).apply {
            text = "显示键盘"
            debounceClick { editText.showKeyboard() }
        }
        val hideKeyBtn = Button(ctx).apply {
            text = "隐藏键盘"
            debounceClick { editText.hideKeyboard() }
        }
        val keyRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            addView(showKeyBtn, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
            addView(hideKeyBtn, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        }
        container.addView(keyRow)

        addTitle("📳 振动")
        val vibrateBtn = Button(ctx).apply {
            text = "短振动 (50ms)"
            debounceClick { ctx.vibrate() }
        }
        container.addView(vibrateBtn)

        addTitle("📋 集合扩展")
        val list = listOf("a", "b", "c")
        addLog("安全连接: ${list.safeJoinToString("|")}")
        list.ifNotEmpty { addLog("非空回调: size=${it.size}") }
        val nullList: List<String>? = null
        addLog("空集合安全连接: '${nullList.safeJoinToString()}'")
    }
}
