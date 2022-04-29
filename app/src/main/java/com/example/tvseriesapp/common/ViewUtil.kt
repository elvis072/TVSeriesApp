package com.example.tvseriesapp.common

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.example.tvseriesapp.R

object ViewUtil {

    val Number.toPx get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics)

    fun cretePlaceholder(context: Context) : CircularProgressDrawable {
        val circularProgressDrawable = CircularProgressDrawable(context)

        circularProgressDrawable.strokeWidth = context.resources.getDimension(R.dimen
            .circular_placeholder_stroke_width).toPx

        circularProgressDrawable.centerRadius = context.resources
            .getDimension(R.dimen.circular_placeholder_radius).toPx

        circularProgressDrawable.start()
        return circularProgressDrawable
    }
}