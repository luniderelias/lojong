package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.PathEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Waterfall(fundamentalsView: FundamentalsView, context: Context) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        globalDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_waterfall)
        width = (0.70915 * screenWidth).toInt()
        height = (0.799089 * width).toInt()
        x += (0.5103 * screenWidth).toInt()
        y = - canvasHeight + (0.9 * screenHeight).toInt() - topBarHeight - bottomBarHeight
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}