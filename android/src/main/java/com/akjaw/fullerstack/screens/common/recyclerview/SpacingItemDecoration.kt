package com.akjaw.fullerstack.screens.common.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.apply {
            if(parent.getChildAdapterPosition(view) == 0){
                top = spacing
            }
            right = spacing
            bottom = spacing
            left = spacing
        }
    }
}
