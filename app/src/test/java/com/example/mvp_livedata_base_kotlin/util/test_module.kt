@file:Suppress("USELESS_CAST")

package com.example.mvp_livedata_base_kotlin.util
import com.example.mvp_livedata_base_kotlin.base.appModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Unconfined
import org.koin.dsl.module.module


val dispatcherTestModule = module {
    factory { Unconfined as CoroutineDispatcher }
}

val testApp = listOf(
        appModule,
        dispatcherTestModule
)