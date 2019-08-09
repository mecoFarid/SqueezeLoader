package com.mecofarid.squeezeloader

import android.view.animation.Interpolator

class EaseInOutCubicInterpolator : Interpolator {

    // easeInOutQuint
    override fun getInterpolation(t: Float): Float {
        var x = t * 2.0f
        if (t < 0.5f) return 0.5f * x * x * x
        x = (t - 0.5f) * 2 - 1
        return 0.5f * x * x * x  + 1
    }
}