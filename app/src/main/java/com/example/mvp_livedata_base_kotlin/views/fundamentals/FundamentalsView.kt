package com.example.mvp_livedata_base_kotlin.views.fundamentals

import android.content.ContentValues.TAG
import android.os.Build
import android.view.MotionEvent
import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.mvp_livedata_base_kotlin.sprite.Background
import com.example.mvp_livedata_base_kotlin.sprite.Character
import com.example.mvp_livedata_base_kotlin.sprite.Elephant
import com.example.mvp_livedata_base_kotlin.sprite.Path


class FundamentalsView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var character: Character
    private var background: Background
    private var path: Path

    private var canvas: Canvas? = null

    init {
        isFocusable = true
        background = Background(this, context)
        character = Elephant(this, context)
        path = Path(this, context)
        holder.addCallback(this)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        performClick()
        if (event.action == MotionEvent.ACTION_DOWN) {
            val pointer = MotionEvent.PointerCoords()
            event.getPointerCoords(0, pointer)
        }
        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
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

    fun drawOnce() {
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
        character.draw(canvas)
        path.draw(canvas)
    }
}