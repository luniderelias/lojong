package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.ElephantOrientationEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Elephant(
    fundamentalsView: FundamentalsView,
    context: Context,
    private var point: PointF,
    private var elephantOrientationEnum: ElephantOrientationEnum
) : Character(fundamentalsView, context) {

    private var initY = 0
    private var elephantToRightPositions = listOf(4, 5, 8, 9, 10, 11)

    private var globalDrawable: Drawable? = null

    init {
        changeElephantOrientation(context, 0)
        width = (0.1274 * screenWidth).toInt()
        height = (0.7681 * width).toInt()
        setPosition()
    }

    private fun setPosition() {
        y = -(point.y * height).toInt() + screenHeight - topBarHeight - bottomBarHeight + initY
        x = (point.x * screenWidth).toInt()
    }

    fun changeElephantOrientation(context: Context, index: Int) {
        elephantOrientationEnum = getElephantOrientation(index)
        globalDrawable = AppCompatResources.getDrawable(
            context,
            if (elephantOrientationEnum == ElephantOrientationEnum.LEFT)
                R.drawable.ic_elephant_to_left
            else
                R.drawable.ic_elephant_to_right
        )
    }

    private fun getElephantOrientation(index: Int): ElephantOrientationEnum {
        return if (elephantToRightPositions.any { it == index })
            ElephantOrientationEnum.RIGHT
        else
            ElephantOrientationEnum.LEFT
    }

    fun moveToPosition(point: PointF) {
        this.point = point
        setPosition()
    }

    override fun move(scrollY: Int) {
        val moveValue = (20 * density).toInt()
        y += if(scrollY > 0 ) moveValue else - moveValue
        initY += if(scrollY > 0 ) moveValue else - moveValue
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}