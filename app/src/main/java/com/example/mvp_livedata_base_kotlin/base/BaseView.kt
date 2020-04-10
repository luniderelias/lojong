package com.example.mvp_livedata_base_kotlin.base

interface BaseView<out T : BasePresenter<*>> {
    val presenter: T
}