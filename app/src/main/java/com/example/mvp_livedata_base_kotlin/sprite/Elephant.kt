package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PointF
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.ElephantOrientationEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView


class Elephant(
    fundamentalsView: FundamentalsView,
    private var context: Context,
    private var point: PointF,
    private var position: Int
) : Character(fundamentalsView, context) {

    private var initY = 0
    private var elephantToRightPositions =
        listOf(4, 5, 8, 9, 10, 11, 13, 14, 16, 17, 18, 19, 20, 22, 23, 28, 29, 30)
    private var elephantRotationAngles = listOf(
        0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f,
        0f, 0f, 90f, 90f, 90f, 90f, 45f, 60f, 90f, 90f,
        0f, 0f, 90f, 60f, 45f, 90f, 90f, 45f, 60f, 60f, 90f
    )

    override var globalDrawable: Drawable? = null
    private lateinit var tempCanvas: Canvas

    init {
        changeElephantDrawable(position)
        rotateDrawable()
        width = (0.1274 * screenWidth).toInt()
        height = (0.7681 * width).toInt()
        setPosition()
    }

    private fun rotateDrawable() {
        globalDrawable = getElephantDrawable(position)
        bitmap = getCurrentBitmap()
        tempCanvas = Canvas(bitmap!!)
        tempCanvas.rotate(angle, bitmap!!.width.toFloat(), bitmap!!.height.toFloat())
        tempCanvas.drawBitmap(bitmap!!, 0f, 0f, null)
    }

    private fun getCurrentBitmap(): Bitmap? {
        return Bitmap.createBitmap(
            globalDrawable!!.intrinsicWidth,
            globalDrawable!!.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }


    private fun setPosition() {
        y = -(point.y * height).toInt() + screenHeight - topBarHeight - bottomBarHeight + initY
        x = (point.x * screenWidth).toInt()
        rotate(elephantRotationAngles[position])
    }

    fun changeElephantDrawable(position: Int) {
        globalDrawable = getElephantDrawable(position)
    }

    private fun getElephantDrawable(position: Int): Drawable? {
        return AppCompatResources.getDrawable(
            context, when (position) {
                in 0..11 -> {
                    if (getElephantOrientation(position) == ElephantOrientationEnum.LEFT)
                        R.drawable.ic_elephant_to_left
                    else
                        R.drawable.ic_elephant_to_right
                }
                in 12..30 -> {
                    if (getElephantOrientation(position) == ElephantOrientationEnum.LEFT)
                        R.drawable.ic_elephant_on_water_to_left
                    else
                        R.drawable.ic_elephant_on_water_to_right
                }
                else -> R.drawable.ic_elephant_to_left
            }
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
        y += if (scrollY > 0) moveValue else -moveValue
        initY += if (scrollY > 0) moveValue else -moveValue
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}