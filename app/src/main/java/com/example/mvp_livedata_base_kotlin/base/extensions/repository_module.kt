package com.example.mvp_livedata_base_kotlin.base.extensions

import com.example.mvp_livedata_base_kotlin.base.LOJONG_BASE
import com.example.mvp_livedata_base_kotlin.data.remote.LojongApi
import com.example.mvp_livedata_base_kotlin.data.remote.LojongRepository
import com.example.mvp_livedata_base_kotlin.data.remote.LojongRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module.module
import retrofit2.Retrofit

val repositoryModule = module {
    factory { get<Retrofit>(LOJONG_BASE).create(LojongApi::class.java) }

    factory { LojongRepositoryImpl(lojongApi = get()) } bind LojongRepository::class
    factory { CoroutineScope(context = Dispatchers.Main) }
}