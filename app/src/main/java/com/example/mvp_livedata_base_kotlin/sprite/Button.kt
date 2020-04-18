package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonStateEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Button(
    fundamentalsView: FundamentalsView,
    context: Context,
    private var buttonStateEnum: ButtonStateEnum,
    day: Int,
    point: PointF
) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null
    private var currentDay: String = ""
    private var textPaint: Paint = Paint()
    private var textX = 0f
    private var textY = 0f

    init {
        setCurrentStateImage(context)
        setValues(point)
        setDay(context, day)
    }

    private fun setValues(point: PointF) {
        width = (0.21 * screenWidth).toInt()
        height = (1.0697 * width).toInt()
        y = -(point.y * height).toInt() + screenHeight - topBarHeight
        x = (point.x * screenWidth).toInt()
        textX = x + (width / 2f)
        textY = (2.1f * height / 3f)
    }

    private fun setCurrentStateImage(context: Context) {
        when (buttonStateEnum) {
            ButtonStateEnum.FIRST_UNLOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_unlocked)
            }
            ButtonStateEnum.FIRST_LOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_locked)
            }
            ButtonStateEnum.SECOND_UNLOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_locked)
            }
            ButtonStateEnum.SECOND_LOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_locked)
            }
        }
    }

    fun getState(): ButtonStateEnum{
        return buttonStateEnum
    }

    fun changeState(context: Context, buttonStateEnum: ButtonStateEnum) {
        this.buttonStateEnum = buttonStateEnum
        when (this.buttonStateEnum) {
            ButtonStateEnum.FIRST_UNLOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_unlocked)
            }
            ButtonStateEnum.FIRST_LOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_locked)
            }
            ButtonStateEnum.SECOND_UNLOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_locked)
            }
            ButtonStateEnum.SECOND_LOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(context, R.drawable.ic_first_button_locked)
            }
        }
    }

    fun setClickListener(xAxis: Int, yAxis: Int, onClickListener: () -> Unit) {
        if (xAxis >= x && xAxis <= x + width && yAxis >= y && yAxis <= y + height)
            onClickListener.invoke()
    }

    private fun setDay(context: Context, day: Int) {
        currentDay = context.getString(R.string.day, day)
        textPaint.color = ContextCompat.getColor(context, R.color.brown_text)
        textPaint.textSize = (0.15f * height)
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
        canvas.drawText(currentDay, textX, y + textY, textPaint)
    }

}