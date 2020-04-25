package com.example.mvp_livedata_base_kotlin.base

import com.example.mvp_livedata_base_kotlin.base.application.ReleaseStartApplication
import com.example.mvp_livedata_base_kotlin.base.application.StarterApplication
import com.example.mvp_livedata_base_kotlin.data.ExampleRepository
import com.example.mvp_livedata_base_kotlin.data.ExampleRepositoryMock
import com.example.mvp_livedata_base_kotlin.data.remote.LojongRepository
import com.example.mvp_livedata_base_kotlin.data.remote.LojongRepositoryImpl
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsContract
import com.example.mvp_livedata_base_kotlin.views.fundamentals.FundamentalsPresenter
import com.example.mvp_livedata_base_kotlin.views.insight.videos.VideosContract
import com.example.mvp_livedata_base_kotlin.views.insight.videos.VideosPresenter
import com.example.mvp_livedata_base_kotlin.views.main.MainContract
import com.example.mvp_livedata_base_kotlin.views.main.MainPresenter
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Main
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory

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

    factory { (view: VideosContract.View) ->
        VideosPresenter(
            view = view,
            lojongRepository = get(),
            dispatcherContext = get()
        )
    } bind VideosContract.Presenter::class
}

val featureModule = module {
    single {
        GsonConverterFactory.create(GsonBuilder().create())
    } bind Converter.Factory::class
}

val dispatcherModule = module {
    factory { Main as CoroutineDispatcher }
}
