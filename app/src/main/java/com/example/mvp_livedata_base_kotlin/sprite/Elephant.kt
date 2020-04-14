package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Elephant(fundamentalsView: FundamentalsView, context: Context) : Character(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        globalDrawable = AppCompatResources.getDrawable(context, R.drawable.elephant)
        width = (0.1274 * screenWidth).toInt()
        height = (0.7681 * width).toInt()
        y = (0.85*screenHeight).toInt() - height
        x = (0.63 * screenWidth).toInt()
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}