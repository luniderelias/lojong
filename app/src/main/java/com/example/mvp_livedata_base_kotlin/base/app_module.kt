package com.example.mvp_livedata_base_kotlin.base

import com.example.mvp_livedata_base_kotlin.base.application.ReleaseStartApplication
import com.example.mvp_livedata_base_kotlin.base.application.StarterApplication
import com.example.mvp_livedata_base_kotlin.data.ExampleRepository
import com.example.mvp_livedata_base_kotlin.data.ExampleRepositoryMock
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsContract
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsPresenter
import com.example.mvp_livedata_base_kotlin.views.main.MainContract
import com.example.mvp_livedata_base_kotlin.views.main.MainPresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Main
import org.koin.dsl.module.module

val appModule = module {
    factory {
        ReleaseStartApplication()
    } bind StarterApplication::class

    factory {
        ExampleRepositoryMock()
    } bind ExampleRepository::class

    factory { (view: MainContract.View) ->
        MainPresenter(
            view = view
        )
    } bind MainContract.Presenter::class

    factory { (view: FundamentalsContract.View) ->
        FundamentalsPresenter(
            view = view,
            repository = get()
        )
    } bind FundamentalsContract.Presenter::class
}

val dispatcherModule = module {
    factory { Main as CoroutineDispatcher }
}