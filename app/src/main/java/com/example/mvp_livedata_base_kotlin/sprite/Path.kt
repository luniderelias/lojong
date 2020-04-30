package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.enums.PathEnum
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

class Path(fundamentalsView: FundamentalsView, context: Context, pathEnum: PathEnum) :
    Sprite(fundamentalsView, context) {

    private var globalDrawable: Drawable? = null

    init {
        getCurrentPath(context, pathEnum)
    }

    private fun getCurrentPath(context: Context, pathEnum: PathEnum) {
        when (pathEnum) {
            PathEnum.FIRST_PATH -> {
                globalDrawable = AppCompatResources.getDrawable(context, R.drawable.first_path)
                height = canvasHeight / 3
                y = - height + screenHeight - topBarHeight - bottomBarHeight
                width = (0.8 * screenWidth).toInt()
                x += (0.1 * screenWidth).toInt()
            }
            PathEnum.SECOND_PATH -> {
                globalDrawable = AppCompatResources.getDrawable(context, R.drawable.second_path)
                height = (2 * canvasHeight) / 3
                y = - height - (canvasHeight / 3) + screenHeight - topBarHeight - bottomBarHeight
                x += (0.082 * screenWidth).toInt()
                width = (0.935 * screenWidth).toInt()
            }
        }
    }

    override fun draw(canvas: Canvas) {
        dst.set(x, y, x + width, y + height)
        globalDrawable?.bounds = dst
        globalDrawable?.draw(canvas)
    }

}