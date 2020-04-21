package com.example.mvp_livedata_base_kotlin.views.main

class MainPresenter(
    override var view: MainContract.View
) : MainContract.Presenter {


    override fun init() {}
}