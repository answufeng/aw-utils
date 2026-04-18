package com.answufeng.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.SubscriptSpan
import android.text.style.SuperscriptSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView

/**
 * 创建带颜色的 SpannableString。
 *
 * ```kotlin
 * textView.text = "Hello World".spanColor(Color.RED, 0, 5)
 * ```
 */
fun String.spanColor(color: Int, start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建带背景色的 SpannableString。
 */
fun String.spanBackgroundColor(color: Int, start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(BackgroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建加粗的 SpannableString。
 */
fun String.spanBold(start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建斜体的 SpannableString。
 */
fun String.spanItalic(start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(StyleSpan(android.graphics.Typeface.ITALIC), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建加粗斜体的 SpannableString。
 */
fun String.spanBoldItalic(start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(StyleSpan(android.graphics.Typeface.BOLD_ITALIC), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建带下划线的 SpannableString。
 */
fun String.spanUnderline(start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(UnderlineSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建带删除线的 SpannableString。
 */
fun String.spanStrikethrough(start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(StrikethroughSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建带字体大小的 SpannableString。
 *
 * @param sizePx 字体大小（**像素**），如需使用 dp 请用 [spanSizeDp] 或手动转换：`spanSize(24.dpToPx(context))`
 * @param start 起始位置
 * @param end 结束位置
 */
fun String.spanSize(sizePx: Int, start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(AbsoluteSizeSpan(sizePx), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建上标的 SpannableString。
 */
fun String.spanSuperscript(start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(SuperscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建下标的 SpannableString。
 */
fun String.spanSubscript(start: Int = 0, end: Int = length): SpannableString {
    return SpannableString(this).apply {
        setSpan(SubscriptSpan(), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * 创建可点击的 SpannableString。
 *
 * 注意：需要设置 `textView.movementMethod = LinkMovementMethod.getInstance()`。
 *
 * @param start 点击区域起始位置
 * @param end 点击区域结束位置
 * @param onClick 点击回调
 * @param underline 是否显示下划线，默认 true
 * @param color 文字颜色，默认为链接蓝色
 */
fun String.spanClickable(
    start: Int = 0,
    end: Int = length,
    onClick: () -> Unit,
    underline: Boolean = true,
    color: Int = Color.parseColor("#2196F3")
): SpannableString {
    return SpannableString(this).apply {
        setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) { onClick() }
            override fun updateDrawState(ds: android.text.TextPaint) {
                super.updateDrawState(ds)
                ds.color = color
                ds.isUnderlineText = underline
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}

/**
 * SpannableStringBuilder 的 DSL 构建器，方便拼接多种样式的文本。
 *
 * ```kotlin
 * textView.text = spannable {
 *     append("Hello ".spanBold())
 *     append("World".spanColor(Color.RED))
 *     append("!".spanUnderline())
 * }
 * ```
 */
fun spannable(builder: SpannableStringBuilder.() -> Unit): SpannableString {
    val sbb = SpannableStringBuilder().apply(builder)
    return SpannableString(sbb)
}

/**
 * 创建带字体大小的 SpannableString（dp 单位）。
 *
 * @param sizeDp 字体大小（dp），内部自动转换为像素
 * @param context Context 用于 dp 转换
 * @param start 起始位置
 * @param end 结束位置
 */
fun String.spanSizeDp(sizeDp: Int, context: android.content.Context, start: Int = 0, end: Int = length): SpannableString {
    return spanSize(sizeDp.dpToPx(context), start, end)
}

/**
 * 设置 TextView 为可点击链接模式。
 */
fun TextView.enableClickableSpan() {
    movementMethod = LinkMovementMethod.getInstance()
    highlightColor = android.graphics.Color.TRANSPARENT
}
