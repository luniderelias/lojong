package com.example.mvp_livedata_base_kotlin.views.fundamentals

import android.content.ContentValues.TAG
import android.os.Build
import android.view.MotionEvent
import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import android.provider.Settings
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonOrientationEnum
import com.example.mvp_livedata_base_kotlin.base.enums.PathEnum
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonStateEnum
import com.example.mvp_livedata_base_kotlin.sprite.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

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
    private var bridge: Bridge
    private var buttonPositions =
        listOf(
            PointF(0.59f, 1.8f),
            PointF(0.59f, 3.5f),
            PointF(0.33f, 4.95f),
            PointF(0.075f, 5.65f),
            PointF(0.39f, 7.35f),
            PointF(0.719f, 7.9f),
            PointF(0.46f, 9.82f),
            PointF(0.218f, 10.08f),
            PointF(0.218f, 11.60f),
            PointF(0.218f, 13.10f),
            PointF(0.43f, 15.36f),
            PointF(0.633f, 15.59f),
            PointF(0.225f, 19.0f),
            PointF(0.225f, 20.5f),
            PointF(0.527f, 22.3f),
            PointF(0.128f, 24.3f),
            PointF(0.128f, 25.7f),
            PointF(0.37f, 28.98f),
            PointF(0.574f, 27.5f),
            PointF(0.574f, 28.6f),
            PointF(0.574f, 29.7f),
            PointF(0.272f, 31.4f),
            PointF(0.672f, 33.2f),
            PointF(0.672f, 34.7f),
            PointF(0.46f, 38.88f),
            PointF(0.225f, 36.7f),
            PointF(0.225f, 38f),
            PointF(0.225f, 39.3f),
            PointF(0.48f, 44f),
            PointF(0.673f, 41.5f),
            PointF(0.673f, 43f)
        )
    private var elephantPositions =
        listOf(
            PointF(0.63f, 1.50f),
            PointF(0.63f, 5.6f),
            PointF(0.6f, 9.6f),
            PointF(0.15f, 10.4f),
            PointF(0.18f, 14.8f),
            PointF(0.69f, 15.4f),
            PointF(0.71f, 20.0f),
            PointF(0.30f, 20.8f),
            PointF(0.25f, 24.4f),
            PointF(0.25f, 28f),
            PointF(0.29f, 31.4f),
            PointF(0.665f, 33.2f),
            PointF(0.43f, 41.0f),
            PointF(0.26f, 44.6f),
            PointF(0.36f, 48.6f),
            PointF(0.33f, 52.8f),
            PointF(0.17f, 56.8f),
            PointF(0.23f, 60.4f),
            PointF(0.59f, 60.9f),
            PointF(0.61f, 63.7f),
            PointF(0.61f, 66.2f),
            PointF(0.46f, 69.5f),
            PointF(0.54f, 73.6f),
            PointF(0.71f, 77.5f),
            PointF(0.68f, 81f),
            PointF(0.31f, 81.5f),
            PointF(0.27f, 85.1f),
            PointF(0.27f, 88.1f),
            PointF(0.3f, 91.7f),
            PointF(0.7f, 92.4f),
            PointF(0.7f, 96.5f),
            PointF(0.5f, 102.5f)
        )
    private var buttons: MutableList<Button> = mutableListOf()
    private var currentPosition = 0
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
            currentPosition
        )
        elephants = Elephants(this, context)
        pathOne = Path(this, context, PathEnum.FIRST_PATH)
        pathTwo = Path(this, context, PathEnum.SECOND_PATH)
        bridge = Bridge(this, context)
        buttonPositions.forEachIndexed { index, pointF ->
            buttons.add(
                Button(
                    this,
                    context,
                    currentPosition,
                    index + 1,
                    pointF
                )
            )
        }
        setOnTouchListener(this)
        holder.addCallback(this)
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
            if(nextPosition < buttons.size) buttons[nextPosition].changeState(context, unlockIndexButtonEnum(nextPosition))
            if (nextPosition > currentPosition) {
                character.moveToPosition(elephantPositions[nextPosition])
                character.changeElephantDrawable(nextPosition)
                currentPosition = nextPosition
                initialClick = Point(0, 0)
                drawOnce()
            }
        }
    }

    private fun unlockIndexButtonEnum(index: Int): ButtonStateEnum {
        return if (index <= 11) ButtonStateEnum.FIRST_UNLOCKED
        else ButtonStateEnum.SECOND_UNLOCKED
    }

    private fun isButtonLocked(button: Button): Boolean {
        return button.getState() == ButtonStateEnum.FIRST_LOCKED || button.getState() == ButtonStateEnum.SECOND_LOCKED
    }

    private fun moveScreen(pt: Point, times: Int) {
        for (x in 0..times) {
            moveScreen(pt)
        }
    }

    private fun moveScreen(pt: Point) {
        try {
            val movingFactor = (pt.y - initialClick.y)
            pathOne.move(movingFactor)
            pathTwo.move(movingFactor)
            topBackground.move(movingFactor)
            waterfall.move(movingFactor)
            elephants.move(movingFactor)
            buttons.forEach { it.move(movingFactor) }
            bridge.move(movingFactor)
            character.move(movingFactor)
            if ((pathOne.y + pathOne.height) >= (pathOne.screenHeight - pathOne.topBarHeight - pathOne.bottomBarHeight) && (topBackground.y) <= 0) {
                draw()
            } else {
                pathOne.move(if ((pathTwo.y) >= (pathTwo.topBarHeight)) -1 else 1)
                pathTwo.move(if ((pathTwo.y) >= (pathTwo.topBarHeight)) -1 else 1)
                topBackground.move(if ((topBackground.y) >= 0) -1 else 1)
                waterfall.move(if ((waterfall.y) >= 0) -1 else 1)
                bridge.move(if ((bridge.y) >= 0) -1 else 1)
                elephants.move(if ((elephants.y) >= 0) -1 else 1)
                character.move(if ((character.y) >= (character.screenHeight) - character.topBarHeight) -1 else 1)
                buttons.forEach { it.move(if (it.y >= (it.screenHeight - it.topBarHeight)) -1 else 1) }
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
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
        if (character.y <= background.screenHeight)
            while (y > (character.y - background.screenHeight / 3)) {
                draw()
                moveScreen(Point(0, 50), 4)
            }
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
        bridge.draw(canvas)
        character.draw(canvas)
    }
}