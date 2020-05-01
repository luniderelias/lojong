package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.ElephantRotationEnum
import com.example.mvp_livedata_base_kotlin.base.enums.toDegrees
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Elephant(
    fundamentalsView: FundamentalsView,
    private var context: Context,
    private var point: PointF,
    private var position: Int
) : Character(fundamentalsView, context) {

    var initY = 0

    private var elephantRotationAngles = listOf(
        180, 180, 180, 180, 0, 0, 180, 180, 0, 0,
        0, 0, 180, 90, 0, 180, 90, 45, 60, 90,
        90, 180, 0, 90, 120, 135, 270, 270,60, 60, 90, 180
    )

    override var globalDrawable: Drawable? = null

    init {
        changeElephantDrawable(position)
        width = (0.1274 * screenWidth).toInt()
        height = (0.7681 * width).toInt()
        setPosition()
    }

    private fun setPosition() {
        y = -(point.y * height).toInt() + screenHeight - topBarHeight - bottomBarHeight + initY
        x = (point.x * screenWidth).toInt()
        rotate(elephantRotationAngles[position].toFloat())
    }

    fun changeElephantDrawable(position: Int) {
        globalDrawable = getElephantDrawable(position)
    }

    private fun getElephantDrawable(position: Int): Drawable? {
        return AppCompatResources.getDrawable(
            context, when (elephantRotationAngles[position].toDegrees()) {
                ElephantRotationEnum.DEGREES_0 -> {
                    if (position < 12) R.drawable.ic_elephant_to_right
                    else R.drawable.ic_elephant_on_water_0_degrees
                }
                ElephantRotationEnum.DEGREES_45 -> {
                    R.drawable.ic_elephant_on_water_45_degrees
                }
                ElephantRotationEnum.DEGREES_60 -> {
                    R.drawable.ic_elephant_on_water_60_degrees
                }
                ElephantRotationEnum.DEGREES_90 -> {
                    R.drawable.ic_elephant_on_water_90_degrees
                }
                ElephantRotationEnum.DEGREES_120 -> {
                    R.drawable.ic_elephant_on_water_120_degrees
                }
                ElephantRotationEnum.DEGREES_135 -> {
                    R.drawable.ic_elephant_on_water_135_degrees
                }
                ElephantRotationEnum.DEGREES_180 -> {
                    if (position < 12) R.drawable.ic_elephant_to_left
                    else R.drawable.ic_elephant_on_water_180_degrees
                }
                ElephantRotationEnum.DEGREES_270 -> {
                    R.drawable.ic_elephant_on_water_270_degrees
                }
            }
        )
    }

    fun moveToPosition(point: PointF) {
        this.point = point
        position += 1
        setPosition()
    }

    override fun move(scrollY: Int) {
        y += getMoveValue(scrollY)
        initY += getMoveValue(scrollY)
    }

    private fun getMoveValue(scrollY: Int): Int {
        val moveValue = (20 * density).toInt()
        return if (scrollY > 0) moveValue else -moveValue
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}