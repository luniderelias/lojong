package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView


abstract class Character(view: FundamentalsView, private var context: Context) :
    Sprite(view, context) {

    var angle: Float = 0f
    abstract var globalDrawable: Drawable?

    open fun rotate(angle: Float) {
        this.angle = angle
    }

}