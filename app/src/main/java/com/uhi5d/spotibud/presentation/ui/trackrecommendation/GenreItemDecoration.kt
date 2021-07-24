package com.uhi5d.spotibud.presentation.ui.trackrecommendation

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GenreItemDecoration(private val margin: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = margin
        outRect.right = margin
        outRect.bottom = margin

        if(parent.getChildAdapterPosition(view) == 0) {
            outRect.top = margin
        }

        
    }
}