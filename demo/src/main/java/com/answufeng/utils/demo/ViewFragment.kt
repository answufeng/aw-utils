package com.answufeng.utils.demo

import android.graphics.Color
import android.view.Gravity
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.answufeng.utils.*
import com.google.android.material.button.MaterialButton

class ViewFragment : BaseDemoFragment() {

    override fun setupDemo() {
        val ctx = requireContext()

        addTitle("防抖点击")
        val debounceBtn = MaterialButton(ctx).apply {
            text = "防抖按钮 (500ms)"
            debounceClick { 
                addLog("防抖点击触发 ✓")
            }
        }
        container.addView(debounceBtn)

        addTitle("扩大触摸区域（expandTouchArea）")
        val touchHost = FrameLayout(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        }
        val tinyTarget = TextView(ctx).apply {
            text = "小控件（热区四边 +32dp）"
            textSize = 10f
            setBackgroundColor(0x33FF9800.toInt())
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
            ).apply { gravity = Gravity.CENTER }
            expandTouchArea(extraDp = 32, context = ctx, delegateParent = touchHost)
            debounceClick { addLog("expandTouchArea 命中 ✓") }
        }
        touchHost.addView(tinyTarget)
        container.addView(touchHost)

        addTitle("可见性控制")
        val targetTv = TextView(ctx).apply {
            text = "我是目标 View"
            textSize = 14f
            setPadding(0, 8, 0, 8)
        }
        container.addView(targetTv)

        val visibleBtn = MaterialButton(ctx).apply {
            text = "VISIBLE"
            debounceClick { targetTv.visible() }
        }
        val goneBtn = MaterialButton(ctx).apply {
            text = "GONE"
            debounceClick { targetTv.gone() }
        }
        val toggleBtn = MaterialButton(ctx).apply {
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

        addTitle("键盘")
        val editText = EditText(ctx).apply {
            hint = "点击后弹出键盘"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        container.addView(editText)

        val showKeyBtn = MaterialButton(ctx).apply {
            text = "显示键盘"
            debounceClick { editText.showKeyboard() }
        }
        val hideKeyBtn = MaterialButton(ctx).apply {
            text = "隐藏键盘"
            debounceClick { editText.hideKeyboard() }
        }
        val keyRow = LinearLayout(ctx).apply {
            orientation = LinearLayout.HORIZONTAL
            addView(showKeyBtn, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
            addView(hideKeyBtn, LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f))
        }
        container.addView(keyRow)

        addTitle("振动")
        val vibrateBtn = MaterialButton(ctx).apply {
            text = "短振动 (50ms)"
            debounceClick { ctx.vibrate() }
        }
        container.addView(vibrateBtn)

        addTitle("集合扩展")
        val list = listOf("a", "b", "c")
        addLog("安全连接: ${list.safeJoinToString("|")}")
        list.ifNotEmpty { addLog("非空回调: size=${it.size}") }
        val nullList: List<String>? = null
        addLog("空集合安全连接: '${nullList.safeJoinToString()}'")
    }
}
