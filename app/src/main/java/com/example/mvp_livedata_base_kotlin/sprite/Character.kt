package com.example.mvp_livedata_base_kotlin.sprite

import android.content.Context
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsView

abstract class Character(view: FundamentalsView, context: Context) : Sprite(view,context) {

    init {
        super.move()
    }

    override fun move() {
        this.x = this.view.width / 10

        super.move()
    }
}