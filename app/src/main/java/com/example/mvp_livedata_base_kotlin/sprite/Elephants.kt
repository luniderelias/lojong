package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.PathEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Elephants(fundamentalsView: FundamentalsView, context: Context) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        globalDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_elephants)
        width = (0.5432 * screenWidth).toInt()
        height = (0.4445 * width).toInt()
        x += (0.0212 * screenWidth).toInt()
        y = - canvasHeight + (0.85 * screenHeight).toInt() - topBarHeight
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}