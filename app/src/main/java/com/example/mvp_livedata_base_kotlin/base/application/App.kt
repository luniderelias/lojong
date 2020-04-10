package com.example.mvp_livedata_base_kotlin.base.application

import android.app.Application
import android.content.Context
import com.example.mvp_livedata_base_kotlin.base.appModule
import com.example.mvp_livedata_base_kotlin.base.dispatcherModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin

class App : Application() {

    companion object {
        lateinit var appContextApplication: Context
    }

    private val starterApplication by inject<StarterApplication>()

    private val modules = listOf(
        appModule,
        dispatcherModule
    )

    override fun onCreate() {
        super.onCreate()
        appContextApplication = this

        startKoin(this, modules)

        starterApplication.start(this)
    }

}