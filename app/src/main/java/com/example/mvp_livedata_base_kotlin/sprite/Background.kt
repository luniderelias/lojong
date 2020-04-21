package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView
import androidx.core.content.res.ResourcesCompat
import com.example.mvp_livedata_base_kotlin.R

class Background(view: FundamentalsView, context: Context) : Sprite(view, context) {


    private var backgroundColor = 0

    init {
        width = screenWidth
        height = canvasHeight - topBarHeight
        y = - height + screenHeight

        backgroundColor = ResourcesCompat.getColor(context.resources, R.color.fundamentals_background_color, null)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawColor(backgroundColor)
    }
}