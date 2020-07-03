package com.mecofarid.squeezeloader

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.UiThread
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import kotlin.math.min


class SqueezeLoader @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0): ProgressBar(context, attrs, defStyleAttr) {

    // Min width of SqueezeLoader: 200dp
    val MIN_WIDTH_SQUEEZELOADER = resources.getDimension(R.dimen.sl_default_squeezeloader_width).toInt()
    // Default squeezebar width: 50dp
    val DEFAULT_WIDTH_SQUEEZEBAR = resources.getDimension(R.dimen.sl_default_squeezebar_width)
    // Maximum squeezebar width: 100dp
    val MAX_WIDTH_SQUEEZEBAR = resources.getDimension(R.dimen.sl_max_squeezebar_width)
    // Min progressbar moving bar width when scaling: 2dp
    val MIN_WIDTH_SQUEEZEBAR = resources.getDimension(R.dimen.sl_min_squeezebar_width)
    // Default height: 4dp
    val MIN_HEIGHT_SQUEEZEBAR = resources.getDimension(R.dimen.sl_default_height).toInt()
    // Default animation duration: 1 second(s)
    val DEFAULT_ANIMATION_DURATION = resources.getInteger(R.integer.sl_default_animation_time)

    val attributes = context.obtainStyledAttributes(attrs, R.styleable.SqueezeLoader, defStyleAttr, 0)
    val mSqueezebarColor = attributes.getColor(R.styleable.SqueezeLoader_sl_colorSqueezebar,
        ContextCompat.getColor(context, R.color.sl_default_color))

    // Animation duration cannot be less than minimum animation duration of this library
    val mAnimatioDuration = Math.max(attributes.getInteger(R.styleable.SqueezeLoader_sl_animationDuration,
        DEFAULT_ANIMATION_DURATION), DEFAULT_ANIMATION_DURATION).toLong()

    // NOTE: maximum visible squeezebar width would be half of given value because the greatest squeezefactor
    // value is 0.5. That's why we're multiplying it by 2 check out `squeezefactor` calculation logic in this file
    // PLUS: Max width of SqueezeBar cannot be biger than MAX_WIDTH_SQUEEZEBAR
    val mSqueezebarWidth = 2 * min(
        attributes.getDimension(R.styleable.SqueezeLoader_sl_squeezebarWidth, DEFAULT_WIDTH_SQUEEZEBAR),
        MAX_WIDTH_SQUEEZEBAR)

    //init paint with custom attrs
    var mSqueezebarPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mSqueezebarColor
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = MIN_WIDTH_SQUEEZEBAR
    }

    var mSqueezebarAnimator: ValueAnimator? = null
    private var mSqueezebarDisplacement = 0f
        set(value) {
            field = value
            ViewCompat.postInvalidateOnAnimation(this)
        }
    private var mAnimationFraction = 0f
    private var mSqueezebarHeight = 0

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mSqueezebarAnimator?.start()
    }

    override fun onDetachedFromWindow() {
        mSqueezebarAnimator?.cancel()
        super.onDetachedFromWindow()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        mSqueezebarAnimator?.let {
            if (it.isStarted && visibility in setOf(View.INVISIBLE, View.GONE)){
                it.cancel()
            }else if (it.isStarted.not() && visibility == View.VISIBLE) {
                it.start()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // Match squeezebar's height to SqueezzeLoader view's height
        mSqueezebarHeight = Math.max(MIN_HEIGHT_SQUEEZEBAR, h)
        mSqueezebarAnimator = ValueAnimator.ofFloat(0f, w.toFloat()).apply {
            addUpdateListener {
                mSqueezebarDisplacement = (it.animatedValue as Float)
                mAnimationFraction = it.animatedFraction
            }
            duration = mAnimatioDuration
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = EaseInOutCubicInterpolator()
            start()
        }
    }

    @Synchronized
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        //Width cannot be less than minimum SqueezeLoader width
        val width = Math.max(MIN_WIDTH_SQUEEZELOADER, widthSize)
        val height:Int

        //`MeasureSpec.EXACTLY` means exact height is given to view
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else {
            //Height cannot be less than minimum SqueezeLoader height
            height = Math.min(MIN_HEIGHT_SQUEEZEBAR, heightSize)
        }
        //MUST CALL THIS
        setMeasuredDimension(width, height)
    }

    @Synchronized
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //draw Squeezebar
        val currentCenterPositionX = mSqueezebarDisplacement
        val squeezeFactor = if(mAnimationFraction <= 0.5) mAnimationFraction else 1-mAnimationFraction
        drawSqueezebar(canvas, currentCenterPositionX, squeezeFactor)
    }

    fun drawSqueezebar(canvas: Canvas, centerPositionX: Float, squeezeFactor: Float){
        val leftPositionX = centerPositionX - squeezeFactor*mSqueezebarWidth/2f
        val rightPositionX = centerPositionX + squeezeFactor*mSqueezebarWidth/2f

        val rectf = RectF().apply {
            left = leftPositionX
            top = 0F
            right = rightPositionX
            bottom = mSqueezebarHeight.toFloat()
        }
        canvas.drawRect(rectf, mSqueezebarPaint)
    }
}