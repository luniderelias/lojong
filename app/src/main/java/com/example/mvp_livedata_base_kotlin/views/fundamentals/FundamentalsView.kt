package com.example.mvp_livedata_base_kotlin.views.fundamentals

import android.content.ContentValues.TAG
import android.os.Build
import android.view.MotionEvent
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonOrientationEnum
import com.example.mvp_livedata_base_kotlin.base.enums.PathEnum
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonStateEnum
import com.example.mvp_livedata_base_kotlin.base.enums.ElephantOrientationEnum
import com.example.mvp_livedata_base_kotlin.sprite.*

class FundamentalsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback, View.OnTouchListener {

    private var character: Elephant
    private var background: Background
    private var topBackground: TopBackground
    private var waterfall: Waterfall
    private var elephants: Elephants
    private var pathOne: Path
    private var pathTwo: Path
    private var buttonPositions =
        listOf(
            PointF(0.59f, 1.8f),
            PointF(0.59f, 3.5f),
            PointF(0.33f, 4.95f),
            PointF(0.075f, 5.65f),
            PointF(0.39f, 7.35f),
            PointF(0.719f, 7.9f)
        )
    private var elephantPositions =
        listOf(
            PointF(0.63f, 1.50f),
            PointF(0.63f, 5.6f),
            PointF(0.6f, 9.6f),
            PointF(0.15f, 10.4f),
            PointF(0.18f, 14.8f)
        )
    private var buttonsHorizontalPositions = listOf(3, 5, 7, 11, 18, 25, 29)
    private var buttons: MutableList<Button> = mutableListOf()
    private var currentPosition = 1
    private var clickedPoint = Point(0, 0)

    private var canvas: Canvas? = null

    init {
        isFocusable = true
        background = Background(this, context)
        topBackground = TopBackground(this, context)
        waterfall = Waterfall(this, context)
        character = Elephant(
            this,
            context,
            elephantPositions[currentPosition],
            ElephantOrientationEnum.LEFT
        )
        elephants = Elephants(this, context)
        pathOne = Path(this, context, PathEnum.FIRST_PATH)
        pathTwo = Path(this, context, PathEnum.SECOND_PATH)
        buttonPositions.forEachIndexed { index, pointF ->
            val currentDay = index + 1
            buttons.add(
                Button(
                    this,
                    context,
                    getCurrentButtonStateEnum(index),
                    getButtonOrientation(currentDay),
                    currentDay,
                    pointF
                )
            )
        }
        setOnTouchListener(this)
        holder.addCallback(this)
    }

    private fun getButtonOrientation(index: Int): ButtonOrientationEnum {
        return if (buttonsHorizontalPositions.any { it == index })
            ButtonOrientationEnum.HORIZONTAL
        else
            ButtonOrientationEnum.VERTICAL
    }

    private fun getCurrentButtonStateEnum(index: Int): ButtonStateEnum {
        return when {
            index <= 11 -> return if (index <= currentPosition) ButtonStateEnum.FIRST_UNLOCKED
            else ButtonStateEnum.FIRST_LOCKED
            index <= currentPosition -> ButtonStateEnum.SECOND_UNLOCKED
            else -> ButtonStateEnum.SECOND_LOCKED
        }
    }

    private var initialClick = Point(0, 0)

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        performClick()
        clickedPoint = Point(event.x.toInt(), event.y.toInt())

        if (event.action == MotionEvent.ACTION_DOWN)
            initialClick = clickedPoint

        buttons.forEachIndexed { index, button ->
            if (isButtonLocked(button))
                return@forEachIndexed
            button.setClickListener(initialClick.x, initialClick.y) {
                goToNextPosition(index)
            }
        }

        if (event.action == MotionEvent.ACTION_MOVE)
            moveScreen(clickedPoint)

        return true
    }

    private fun goToNextPosition(index: Int) {
        if (index < buttonPositions.size) {
            val nextPosition = index + 1
            buttons[nextPosition].changeState(context, unlockIndexButtonEnum(nextPosition))
            if (nextPosition > currentPosition) {
                character.moveToPosition(elephantPositions[nextPosition])
                character.changeElephantOrientation(context, nextPosition)
                currentPosition = nextPosition
            }
            draw()
        }
    }

    private fun unlockIndexButtonEnum(index: Int): ButtonStateEnum {
        return if (index <= 11) ButtonStateEnum.FIRST_UNLOCKED
        else ButtonStateEnum.SECOND_UNLOCKED
    }

    private fun isButtonLocked(button: Button): Boolean {
        return button.getState() == ButtonStateEnum.FIRST_LOCKED || button.getState() == ButtonStateEnum.SECOND_LOCKED
    }

    private fun moveScreen(pt: Point) {
        val movingFactor = (pt.y - initialClick.y)
        pathOne.move(movingFactor)
        pathTwo.move(movingFactor)
        topBackground.move(movingFactor)
        waterfall.move(movingFactor)
        elephants.move(movingFactor)
        buttons.forEach { it.move(movingFactor) }
        character.move(movingFactor)
        if ((pathOne.y + pathOne.height) >= (pathOne.screenHeight - pathOne.topBarHeight - pathOne.bottomBarHeight) && (topBackground.y) <= 0) {
            draw()
        } else {
            pathOne.move(if ((pathTwo.y) >= (pathTwo.topBarHeight)) -1 else 1)
            pathTwo.move(if ((pathTwo.y) >= (pathTwo.topBarHeight)) -1 else 1)
            topBackground.move(if ((topBackground.y) >= 0) -1 else 1)
            waterfall.move(if ((waterfall.y) >= 0) -1 else 1)
            elephants.move(if ((elephants.y) >= 0) -1 else 1)
            character.move(if ((character.y) >= (character.screenHeight) - character.topBarHeight) -1 else 1)
            buttons.forEach { it.move(if (it.y >= (it.screenHeight - it.topBarHeight)) -1 else 1) }
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
        topBackground.draw(canvas)
        pathOne.draw(canvas)
        pathTwo.draw(canvas)
        waterfall.draw(canvas)
        elephants.draw(canvas)
        buttons.forEach { it.draw(canvas) }
        character.draw(canvas)
    }
}