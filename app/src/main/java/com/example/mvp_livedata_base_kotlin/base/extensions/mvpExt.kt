package com.example.mvp_livedata_base_kotlin.base.extensions


import android.content.ComponentCallbacks
import com.example.mvp_livedata_base_kotlin.base.BasePresenter
import com.example.mvp_livedata_base_kotlin.base.BaseView
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

inline fun <reified T : BasePresenter<*>> ComponentCallbacks.injectPresenter(
    view: BaseView<T>,
    name: String = "",
    scope: Scope? = null
) = lazy { get<T>(name, scope) { parametersOf(view) } }