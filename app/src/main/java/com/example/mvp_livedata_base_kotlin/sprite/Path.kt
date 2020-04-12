package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Path(fundamentalsView: FundamentalsView, context: Context) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        globalDrawable = AppCompatResources.getDrawable(context, R.drawable.first_path)
        width = (0.9 * screenWidth).toInt()
        height = canvasHeight / 3

        y = -height + screenHeight - toolbarHeight - statusBarHeight
    }

    override fun draw(canvas: Canvas) {
        dst.set(x + (0.1 * screenWidth).toInt(), y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}