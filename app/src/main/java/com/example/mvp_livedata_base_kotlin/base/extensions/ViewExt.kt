package com.example.mvp_livedata_base_kotlin.base.extensions

import android.view.View

val Boolean.shouldShowView: Int
    get() = if (this) View.VISIBLE
    else View.GONE