package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.PathEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class TopBackground(fundamentalsView: FundamentalsView, context: Context) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        globalDrawable = AppCompatResources.getDrawable(context, R.drawable.top_background)
        y = - canvasHeight + (0.3 * screenHeight).toInt() - bottomBarHeight
        width = (1.935 * screenWidth).toInt()
        height = (0.5088 * width).toInt()
        x -= (0.3 * screenWidth).toInt()
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}