package com.uhi5d.spotibud.presentation.ui.search

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SearchResultsItemDecoration(private val margin: Int): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = margin
        outRect.left = margin
        outRect.bottom = margin

        if (parent.getChildLayoutPosition(view)==0) outRect.top = margin
    }
}