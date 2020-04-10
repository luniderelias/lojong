package com.example.mvp_livedata_base_kotlin.base

interface BasePresenter<T> {
    var view: T
}