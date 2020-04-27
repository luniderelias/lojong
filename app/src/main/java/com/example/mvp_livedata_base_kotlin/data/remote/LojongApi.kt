package com.example.mvp_livedata_base_kotlin.data.remote

import com.example.mvp_livedata_base_kotlin.data.model.ArticleItem
import com.example.mvp_livedata_base_kotlin.data.model.ArticlesResponse
import com.example.mvp_livedata_base_kotlin.data.model.QuoteItem
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import retrofit2.Call
import retrofit2.http.GET

interface LojongApi {

    @GET("videos")
    fun getVideos(): Call<List<VideoItem>>

    @GET("articles")
    fun getArticles(): Call<ArticlesResponse>

    @GET("quotes")
    fun getQuotes(): Call<List<QuoteItem>>
}