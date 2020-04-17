package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonStateEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Button(fundamentalsView: FundamentalsView, context: Context, buttonStateEnum: ButtonStateEnum, day: Int) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        getCurrentPath(context, buttonStateEnum)
    }

    private fun getCurrentPath(context: Context, buttonStateEnum: ButtonStateEnum) {
        when (buttonStateEnum) {
                ButtonStateEnum.FIRST_UNLOCKED -> {
                globalDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_first_button_unlocked)
                width = (0.21 * screenWidth).toInt()
                height = (1.0697 *width).toInt()
                y = - (1.8 * height).toInt() + screenHeight - topBarHeight
                x = (0.59 * screenWidth).toInt()
            }
            ButtonStateEnum.FIRST_LOCKED -> {
            }
            ButtonStateEnum.SECOND_UNLOCKED -> {
            }
            ButtonStateEnum.SECOND_LOCKED -> {
            }
        }
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}