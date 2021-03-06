@file:Suppress("unused")

package com.bell.calorieda.util

import android.content.res.Resources

object DimensionUtils {

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }
}