package com.answufeng.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 平滑滚动到列表顶部。
 */
fun RecyclerView.smoothScrollToTop() {
    val lm = layoutManager ?: return
    if (lm.itemCount == 0) return
    smoothScrollToPosition(0)
}

/**
 * 为列表项添加垂直间距（首项不加 top 间距）。
 *
 * @param spacingDp 间距（dp）
 */
fun RecyclerView.addVerticalSpacing(
    spacingDp: Int,
    context: Context = this.context,
) {
    require(spacingDp >= 0) { "spacingDp must be >= 0, got $spacingDp" }
    val spacingPx = spacingDp.dpToPx(context)
    addItemDecoration(
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State,
            ) {
                val position = parent.getChildAdapterPosition(view)
                if (position > 0) {
                    outRect.top = spacingPx
                }
            }
        },
    )
}
