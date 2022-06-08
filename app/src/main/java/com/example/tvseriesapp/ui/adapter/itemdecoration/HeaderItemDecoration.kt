package com.example.tvseriesapp.ui.adapter.itemdecoration

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.example.tvseriesapp.R
import com.example.tvseriesapp.common.ViewUtil.getAttribute
import com.example.tvseriesapp.common.ViewUtil.toPx

class HeaderItemDecoration(private val getHeader: (position: Int) -> String) : RecyclerView.ItemDecoration() {

    private val paintTextSize = 20.toPx
    private val paint = Paint().apply {
        strokeWidth = 10.toPx
        textSize = paintTextSize
        isLinearText = true
        isAntiAlias = true
        isFakeBoldText = true
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        parent.context?.let {
            paint.color = it.getAttribute(com.google.android.material.R.attr.colorSecondary)
        }

        parent.children.forEach { child ->
            val adapterPosition = parent.getChildAdapterPosition(child)
                .let { if (it == RecyclerView.NO_POSITION) return@forEach else it }

            val header = getHeader(adapterPosition)
            if (!isHeader(header, adapterPosition))
                return@forEach

            val left = child.left + child.paddingStart
            val top = child.top
            canvas.drawText(header, left.toFloat(), top.toFloat(), paint)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val adapterPosition = parent.getChildAdapterPosition(view)
            .let { if (it == RecyclerView.NO_POSITION) return else it }

        if (!isHeader(getHeader(adapterPosition), adapterPosition))
            return

        outRect.top = paintTextSize.toInt()
        if (adapterPosition > 0)
            parent.context?.resources?.let {
                outRect.top += it.getDimension(R.dimen.tv_show_margin).toInt()
            }
    }

    private fun isHeader(header: String, position: Int): Boolean {
        return position == 0 || (header != "" && position > 0 && getHeader(position - 1) != header)
    }
}