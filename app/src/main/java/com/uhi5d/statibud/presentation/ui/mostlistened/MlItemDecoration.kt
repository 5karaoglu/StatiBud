package com.uhi5d.statibud.presentation.ui.mostlistened

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MlItemDecoration(
    private val margin: Int
) : RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = margin
        outRect.right = margin
        outRect.bottom = margin

        if (parent.getChildLayoutPosition(view) == 0){
            outRect.top = margin
        }
    }
}