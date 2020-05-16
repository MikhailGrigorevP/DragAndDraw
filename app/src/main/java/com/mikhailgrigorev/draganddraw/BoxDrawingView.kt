package com.mikhailgrigorev.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class BoxDrawingView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {
    private var mCurrentBox: Box? = null
    private val mBoxen: MutableList<Box> =
        ArrayList()
    private val mBoxPaint: Paint
    private val mBackgroundPaint: Paint

    // Used when creating the view in code
    constructor(context: Context?) : this(context, null) {}

    protected override fun onDraw(canvas: Canvas) {
        // Fill the background
        canvas.drawPaint(mBackgroundPaint)
        for (box in mBoxen) {
            val left: Float = Math.min(box.origin.x, box.current.x)
            val right: Float = Math.max(box.origin.x, box.current.x)
            val top: Float = Math.min(box.origin.y, box.current.y)
            val bottom: Float = Math.max(box.origin.y, box.current.y)
            canvas.drawRect(left, top, right, bottom, mBoxPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // Reset drawing state
                mCurrentBox = Box(current)
                mBoxen.add(mCurrentBox!!)
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                if (mCurrentBox != null) {
                    mCurrentBox!!.current = current
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                mCurrentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                mCurrentBox = null
            }
        }
        Log.i(
            TAG, action + " at x=" + current.x +
                    ", y=" + current.y
        )
        return true
    }

    companion object {
        private const val TAG = "BoxDrawingView"
    }

    // Used when inflating the view from XML
    init {

        // Paint the boxes a nice semitransparent red (ARGB)
        mBoxPaint = Paint()
        mBoxPaint.setColor(0x22ff0000)

        // Paint the background off-white
        mBackgroundPaint = Paint()
        mBackgroundPaint.setColor(-0x71020)
    }
}
