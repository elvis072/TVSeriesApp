package com.example.tvseriesapp.common

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.tvseriesapp.R

object ViewUtil {

    // convert dp to pixel
    val Number.toPx get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics)

    // get attribute from theme
    fun Context.getAttribute(@AttrRes attribute: Int) = TypedValue()
        .apply { theme.resolveAttribute(attribute, this, true) }
        .data

    fun cretePlaceholder(context: Context) = CircularProgressDrawable(context).apply {
        strokeWidth = context.resources.getDimension(R.dimen.circular_placeholder_stroke_width)
        centerRadius = context.resources.getDimension(R.dimen.circular_placeholder_radius)
        setColorSchemeColors(context.getAttribute(com.google.android.material.R.attr.colorPrimary))
        start()
    }
}