package com.answufeng.utils.demo

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

abstract class BaseDemoFragment : Fragment() {

    protected lateinit var container: LinearLayout

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
        TextView(requireContext()).apply {
            this.text = text
            textSize = 16f
            setTypeface(null, Typeface.BOLD)
            setTextColor(resources.getColor(R.color.on_surface, null))
            setPadding(0, 24, 0, 8)
            container.addView(this)
        }
    }

    protected fun addLog(text: String) {
        TextView(requireContext()).apply {
            this.text = text
            textSize = 13f
            setTextColor(resources.getColor(R.color.on_surface_variant, null))
            setPadding(16, 4, 0, 4)
            container.addView(this)
        }
    }

    protected fun addSeparator() {
        View(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1
            ).apply { setMargins(0, 8, 0, 8) }
            setBackgroundColor(resources.getColor(R.color.card_border, null))
            container.addView(this)
        }
    }
}
