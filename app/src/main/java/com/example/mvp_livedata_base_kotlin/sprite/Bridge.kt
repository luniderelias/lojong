package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Bridge(fundamentalsView: FundamentalsView, context: Context) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        globalDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_bridge)
        width = (0.18 * screenWidth).toInt()
        height = (1.67318 * width).toInt()
        x += (0.648 * screenWidth).toInt()
        y = - (0.34 * canvasHeight).toInt() + screenHeight - topBarHeight - bottomBarHeight
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}