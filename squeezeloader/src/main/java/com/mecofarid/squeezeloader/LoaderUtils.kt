package com.mecofarid.squeezeloader

import android.content.Context
import android.util.DisplayMetrics



class LoaderUtils {
    companion object {
        fun convertDpToPixel(dp: Float, context: Context): Float
                = context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT.toFloat() * dp
    }
}