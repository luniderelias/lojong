package com.example.mvp_livedata_base_kotlin.views.main

import com.example.mvp_livedata_base_kotlin.base.BasePresenter
import com.example.mvp_livedata_base_kotlin.base.BaseView

interface MainContract {
    interface View : BaseView<Presenter> {
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter : BasePresenter<View> {
        fun init()
    }
}