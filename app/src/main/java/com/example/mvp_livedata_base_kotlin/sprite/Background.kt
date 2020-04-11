package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R

class Background(view: FundamentalsView, context: Context) : Sprite(view, context) {

    var globalDrawable: Drawable? = null

    init {
        globalDrawable = AppCompatResources.getDrawable(context, R.drawable.elephant_positions)
        width = screenWidth
        height = screenHeight
    }


    override fun draw(canvas: Canvas) {
        dst.set(x, y, x+width, y+height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }
}