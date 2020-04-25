package com.example.mvp_livedata_base_kotlin.base

import android.util.Log
import com.example.mvp_livedata_base_kotlin.BuildConfig
import com.example.mvp_livedata_base_kotlin.base.extensions.toCurl
import okhttp3.*
import org.koin.dsl.module.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val HEADER_INTERCEPTOR = "HEADER_INTERCEPTOR"
private const val CURL_INTERCEPTOR = "CURL_INTERCEPTOR"

const val LOJONG_BASE = "LOJONG_BASE"

val retrofitClientModule = module {

    single(LOJONG_BASE) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(get(LOJONG_BASE))
            .addConverterFactory(get())
            .build()
    } bind Retrofit::class

    @Suppress("ConstantConditionIf")
    single(LOJONG_BASE) {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(get(HEADER_INTERCEPTOR))

        if (BuildConfig.DEBUG)
            builder.addInterceptor(get(CURL_INTERCEPTOR))

        builder.build()
    } bind OkHttpClient::class

    single(HEADER_INTERCEPTOR) {
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", BuildConfig.AUTHORIZATION)
                    .build()
            )
        }
    }

    @Suppress("ConstantConditionIf")
    single(CURL_INTERCEPTOR) {
        Interceptor { chain ->
            chain.request().let { request ->
                Log.i("REQUEST_LOG", request.toCurl())
                chain.proceed(request)
            }
        }
    }
}
