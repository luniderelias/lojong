package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView


abstract class Sprite(
    protected var view: FundamentalsView,
    context: Context
) {

    protected var bitmap: Bitmap? = null

    var height: Int = 0
    var width: Int = 0

    var screenHeight: Int = 0
    var screenWidth: Int = 0

    var x: Int = 0
    var y: Int = 0
    var angle: Float = 0F
    protected var src: Rect = Rect()
    protected var dst: Rect = Rect()

    protected var col: Byte = 0
    protected var row: Byte = 0

    protected var colNr: Byte = 1
    protected var frameTimeCounter: Short = 0

    init {
        screenHeight = context.resources.displayMetrics.heightPixels
        screenWidth = context.resources.displayMetrics.widthPixels
    }

    open fun draw(canvas: Canvas) {
        src.set(col * width, row * height, (col + 1) * width, (row + 1) * height)
        dst.set(x, y, x + width, y + height)
        canvas.drawBitmap(bitmap!!, src, dst, null)
        canvas.rotate(angle)
    }

    open fun move() {
        x += 100
        y += 100
    }

    open fun rotate(angle: Float){
        this.angle = angle
    }

}