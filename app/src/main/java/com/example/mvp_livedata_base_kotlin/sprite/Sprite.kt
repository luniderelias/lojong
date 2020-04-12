package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView


abstract class Sprite(
    protected var view: FundamentalsView,
    context: Context
) {

    protected var bitmap: Bitmap? = null

    var height: Int = 0
    var width: Int = 0
    var canvasHeight: Int = 0

    var screenHeight: Int = 0
    var screenWidth: Int = 0
    var toolbarHeight: Int = 0
    var statusBarHeight: Int = 0

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
        getToolbarHeight(context)
        getStatusBarHeight(context)
        canvasHeight = (11.5 * screenWidth).toInt() - toolbarHeight - statusBarHeight
    }

    private fun getToolbarHeight(context: Context) {
        val tv = TypedValue()
        if (context.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true))
            toolbarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
    }

    private fun getStatusBarHeight(context: Context){
        val resId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resId > 0) {
            statusBarHeight = context.resources.getDimensionPixelSize(resId)
        }
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

    open fun rotate(angle: Float) {
        this.angle = angle
    }

}