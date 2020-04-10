package com.example.mvp_livedata_base_kotlin.main

import androidx.lifecycle.MutableLiveData
import com.example.mvp_livedata_base_kotlin.base.BasePresenter
import com.example.mvp_livedata_base_kotlin.base.BaseView
import com.example.mvp_livedata_base_kotlin.data.ExampleData

interface MainContract {
    interface View : BaseView<Presenter> {
        fun initPresenter()
        fun observeData()
        fun onReload()
        fun handleMessageVisibility(shouldShow: Boolean)
        fun handleLoadingVisibility(shouldShow: Boolean)
    }
    interface Presenter : BasePresenter<View> {
        fun init()
        fun loadData()
        fun observeForExampleData(): MutableLiveData<ExampleData>
    }
}