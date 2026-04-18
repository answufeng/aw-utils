package com.answufeng.utils

import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.inputmethod.EditorInfo
import android.widget.EditText

/**
 * 设置文本变化监听（仅回调 afterTextChanged）。
 *
 * ```kotlin
 * editText.onTextChanged { text ->
 *     viewModel.onInputChanged(text)
 * }
 * ```
 */
fun EditText.onTextChanged(listener: (text: String) -> Unit) {
    addTextChangedListener(object : android.text.TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: android.text.Editable?) {
            listener(s?.toString() ?: "")
        }
    })
}

/**
 * 设置输入最大长度过滤器。
 *
 * @param maxLength 最大字符数
 */
fun EditText.setMaxLength(maxLength: Int) {
    val current = filters.toMutableList()
    current.removeAll { it is LengthFilter }
    current.add(LengthFilter(maxLength))
    filters = current.toTypedArray()
}

/**
 * 添加输入过滤器。
 */
fun EditText.addFilter(filter: InputFilter) {
    val current = filters.toMutableList()
    current.add(filter)
    filters = current.toTypedArray()
}

/**
 * 限制小数位数输入过滤器。
 *
 * ```kotlin
 * editText.addDecimalFilter(2)
 * ```
 */
fun EditText.addDecimalFilter(maxDecimalPlaces: Int): InputFilter {
    val filter = DecimalInputFilter(maxDecimalPlaces)
    addFilter(filter)
    return filter
}

/**
 * 清除焦点并隐藏键盘。
 */
fun EditText.clearFocusAndHideKeyboard() {
    clearFocus()
    hideKeyboard()
}

/**
 * 设置软键盘动作按钮监听。
 *
 * ```kotlin
 * editText.setOnEditorAction(EditorInfo.IME_ACTION_SEARCH) {
 *     performSearch()
 * }
 * ```
 */
fun EditText.setOnEditorAction(actionId: Int, listener: () -> Unit) {
    setOnEditorActionListener { _, id, _ ->
        if (id == actionId) {
            listener()
            true
        } else false
    }
}

internal class DecimalInputFilter(private val maxDecimalPlaces: Int) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: android.text.Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val newText = (dest?.toString() ?: "").let { text ->
            text.substring(0, dstart) + (source?.toString() ?: "") + text.substring(dend)
        }
        val dotIndex = newText.indexOf('.')
        if (dotIndex >= 0 && newText.length - dotIndex - 1 > maxDecimalPlaces) {
            return ""
        }
        return null
    }
}
