package com.answufeng.utils.demo

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar

abstract class BaseDemoFragment : Fragment() {

    protected lateinit var container: LinearLayout
    private var currentSectionContent: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_demo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view.findViewById(R.id.container)
        setupDemo()
    }

    abstract fun setupDemo()

    protected fun addTitle(text: String) {
        val ctx = requireContext()
        val horizontalPadding = dp(16)

        val card = MaterialCardView(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = dp(14) }
            radius = dp(16).toFloat()
            cardElevation = dp(1).toFloat()
            setCardBackgroundColor(MaterialColors.getColor(this, com.google.android.material.R.attr.colorSurfaceContainer))
        }

        val cardRoot = LinearLayout(ctx).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL
            setPadding(horizontalPadding, dp(14), horizontalPadding, dp(12))
        }

        val titleView = TextView(ctx).apply {
            this.text = text
            textSize = 15f
            setTypeface(null, Typeface.BOLD)
            setTextColor(MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSurface))
        }

        val content = LinearLayout(ctx).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { topMargin = dp(10) }
            orientation = LinearLayout.VERTICAL
        }

        cardRoot.addView(titleView)
        cardRoot.addView(content)
        card.addView(cardRoot)

        container.addView(card)
        currentSectionContent = content
    }

    protected fun addLog(text: String) {
        val host = currentSectionContent ?: run {
            addTitle("输出")
            currentSectionContent!!
        }

        TextView(requireContext()).apply {
            this.text = text
            textSize = 13f
            setTextColor(MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSurfaceVariant))
            setPadding(0, dp(6), 0, dp(6))
            isClickable = true
            isFocusable = true

            // Tap to copy; long-press to copy (accessibility friendly).
            setOnClickListener {
                copyToClipboard(text)
                Snackbar.make(requireView(), "已复制", Snackbar.LENGTH_SHORT).show()
            }
            setOnLongClickListener {
                copyToClipboard(text)
                Snackbar.make(requireView(), "已复制", Snackbar.LENGTH_SHORT).show()
                true
            }

            host.addView(this)
        }
    }

    protected fun addSeparator() {
        val host = currentSectionContent ?: container
        View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(1)
            ).apply { topMargin = dp(8); bottomMargin = dp(8) }
            setBackgroundColor(MaterialColors.getColor(this, com.google.android.material.R.attr.colorOutlineVariant))
            host.addView(this)
        }
    }

    private fun dp(value: Int): Int {
        val density = resources.displayMetrics.density
        return (value * density).toInt()
    }

    private fun copyToClipboard(text: String) {
        val clipboard = requireContext().getSystemService<android.content.ClipboardManager>()
        clipboard?.setPrimaryClip(android.content.ClipData.newPlainText("demo", text))
    }
}
