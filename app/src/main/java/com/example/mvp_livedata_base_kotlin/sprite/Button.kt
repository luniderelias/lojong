package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonOrientationEnum
import com.example.mvp_livedata_base_kotlin.base.enums.ButtonStateEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Button(
    fundamentalsView: FundamentalsView,
    context: Context,
    private var currentPosition: Int,
    private var day: Int,
    point: PointF
) :
    Sprite(fundamentalsView, context) {

    private var buttonsHorizontalPositions = listOf(3, 5, 7, 11, 18, 25, 29)
    private var globalDrawable: Drawable? = null
    private var currentDay: String = ""
    private var textPaint: Paint = Paint()
    private var textX = 0f
    private var textY = 0f

    private var buttonStateEnum: ButtonStateEnum
    private var buttonOrientationEnum: ButtonOrientationEnum

    init {
        buttonStateEnum = getCurrentButtonStateEnum(day)
        buttonOrientationEnum = getButtonOrientation(day)
        setCurrentStateImage(context)
        setValues(point)
        setDay(context, day)
    }

    private fun setValues(point: PointF) {
        if(getButtonOrientation(day) == ButtonOrientationEnum.VERTICAL) {
            width = (0.21 * screenWidth).toInt()
            height = (1.0697 * width).toInt()
        } else {
            width = (0.21 * screenWidth).toInt()
            height = (0.98 * width).toInt()
        }
        y = -(point.y * height).toInt() + screenHeight - topBarHeight - bottomBarHeight
        x = (point.x * screenWidth).toInt()
        textX = x + (width / 2f)
        textY = (2.1f * height / 3f)
    }

    private fun getButtonOrientation(index: Int): ButtonOrientationEnum {
        return if (buttonsHorizontalPositions.any { it == index })
            ButtonOrientationEnum.HORIZONTAL
        else
            ButtonOrientationEnum.VERTICAL
    }

    private fun getCurrentButtonStateEnum(index: Int): ButtonStateEnum {
        return when {
            index <= 12 -> return if (index <= (currentPosition + 1)) ButtonStateEnum.FIRST_UNLOCKED
            else ButtonStateEnum.FIRST_LOCKED
            index <= (currentPosition + 1) -> ButtonStateEnum.SECOND_UNLOCKED
            else -> ButtonStateEnum.SECOND_LOCKED
        }
    }

    private fun setCurrentStateImage(context: Context) {
        changeState(context, buttonStateEnum)
    }

    fun getState(): ButtonStateEnum {
        return buttonStateEnum
    }

    fun changeState(context: Context, buttonStateEnum: ButtonStateEnum) {
        this.buttonStateEnum = buttonStateEnum
        when (this.buttonStateEnum) {
            ButtonStateEnum.FIRST_UNLOCKED -> {
                globalDrawable = AppCompatResources.getDrawable(
                    context,
                    if (buttonOrientationEnum == ButtonOrientationEnum.VERTICAL)
                        R.drawable.ic_first_button_vertical_unlocked
                    else
                        R.drawable.ic_first_button_horizontal_unlocked
                )
            }
            ButtonStateEnum.FIRST_LOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(
                        context,
                        if (buttonOrientationEnum == ButtonOrientationEnum.VERTICAL)
                            R.drawable.ic_first_button_vertical_locked
                        else
                            R.drawable.ic_first_button_horizontal_locked
                    )
            }
            ButtonStateEnum.SECOND_UNLOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(
                        context,
                        if (buttonOrientationEnum == ButtonOrientationEnum.VERTICAL)
                            R.drawable.ic_second_button_vertical_unlocked
                        else
                            R.drawable.ic_second_button_horizontal_unlocked
                    )
            }
            ButtonStateEnum.SECOND_LOCKED -> {
                globalDrawable =
                    AppCompatResources.getDrawable(
                        context,
                        if (buttonOrientationEnum == ButtonOrientationEnum.VERTICAL)
                            R.drawable.ic_second_button_vertical_locked
                        else
                            R.drawable.ic_second_button_horizontal_locked
                    )
            }
        }
    }

    fun setClickListener(xAxis: Int, yAxis: Int, onClickListener: () -> Unit) {
        if (xAxis >= x && xAxis <= x + width && yAxis >= y && yAxis <= y + height)
            onClickListener.invoke()
    }

    private fun setDay(context: Context, day: Int) {
        currentDay = context.getString(R.string.day, day)
        textPaint.color = getDayTextColor(context, day)
        textPaint.textSize = (0.15f * height)
        textPaint.textAlign = Paint.Align.CENTER
    }

    private fun getDayTextColor(context: Context, day: Int): Int{
        return if(day <= 12) ContextCompat.getColor(context, R.color.brown_text)
        else ContextCompat.getColor(context, R.color.white)
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
        canvas.drawText(currentDay, textX, y + textY, textPaint)
    }

}