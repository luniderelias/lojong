package com.example.mvp_livedata_base_kotlin.views.fundamentals

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Build
import android.view.MotionEvent
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.mvp_livedata_base_kotlin.base.enums.PathEnum
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonStateEnum
import com.example.mvp_livedata_base_kotlin.sprite.*


class FundamentalsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var character: Elephant
    private var background: Background
    private var topBackground: TopBackground
    private var waterfall: Waterfall
    private var elephants: Elephants
    private var pathOne: Path
    private var pathTwo: Path
    private var buttonOne: Button

    private var offsetY: Int = 0

    private var canvas: Canvas? = null

    init {
        isFocusable = true
        background = Background(this, context)
        topBackground = TopBackground(this, context)
        waterfall = Waterfall(this, context)
        character = Elephant(this, context)
        elephants = Elephants(this, context)
        pathOne = Path(this, context, PathEnum.FIRST_PATH)
        pathTwo = Path(this, context, PathEnum.SECOND_PATH)
        buttonOne = Button(this, context, ButtonStateEnum.FIRST_UNLOCKED, 1)
        holder.addCallback(this)
    }

    private var initialClick = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()
        val pt = Point(event.x.toInt(), event.y.toInt())
        if (event.action == MotionEvent.ACTION_DOWN) {
            initialClick = pt.y
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            moveScreen(pt)
        }
        return true
    }

    private fun moveScreen(pt: Point){
        val movingFactor = (pt.y - initialClick)
        pathOne.move(movingFactor)
        pathTwo.move(movingFactor)
        topBackground.move(movingFactor)
        waterfall.move(movingFactor)
        elephants.move(movingFactor)
        character.move(movingFactor)
        buttonOne.move(movingFactor)
        if ((pathOne.y + pathOne.height) >= pathOne.screenHeight && (topBackground.y) <= 0) {
            draw()
        } else {
            pathOne.move(if((pathTwo.y) >= (pathTwo.topBarHeight)) -1 else 1)
            pathTwo.move(if((pathTwo.y) >= (pathTwo.topBarHeight)) -1 else 1)
            topBackground.move(if((topBackground.y) >= 0) -1 else 1)
            waterfall.move(if((waterfall.y) >= 0) -1 else 1)
            elephants.move(if((elephants.y) >= 0) -1 else 1)
            character.move(if((character.y) >= ((0.85*character.screenHeight) - character.height)) -1 else 1)
            buttonOne.move(if((character.y) >= ((0.85*character.screenHeight) - character.height)) -1 else 1)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        drawOnce()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    private fun getCanvas(): Canvas {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.surface.lockHardwareCanvas()
        } else {
            holder.lockCanvas()
        }
    }

    private fun drawOnce() {
        draw()
    }

    private fun draw() {
        canvas = null
        try {
            synchronized(holder) {
                canvas = getCanvas()
                drawCanvas(canvas!!)
            }
        } catch (ex: Exception) {
            Log.d(TAG, "Error", ex)
        } finally {
            if (canvas != null) {
                holder.surface.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun drawCanvas(canvas: Canvas) {
        background.draw(canvas)
        pathOne.draw(canvas)
        pathTwo.draw(canvas)
        topBackground.draw(canvas)
        waterfall.draw(canvas)
        elephants.draw(canvas)
        character.draw(canvas)
        buttonOne.draw(canvas)
    }
}