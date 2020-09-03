package com.jayvijaygohil.githubusers.feature.user_info.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jayvijaygohil.githubusers.R

class SpaceItemDecoration(
    private val context: Context
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when (parent.getChildAdapterPosition(view)) {
            RecyclerView.NO_POSITION -> return
            else -> outRect.set(
                0,
                context.resources.getDimensionPixelSize(R.dimen.repository_entity_top_margin),
                0,
                0
            )
        }
    }
}