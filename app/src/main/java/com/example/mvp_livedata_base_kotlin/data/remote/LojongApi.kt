package com.example.mvp_livedata_base_kotlin.data.remote

import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import retrofit2.Call
import retrofit2.http.GET

interface LojongApi {
    @GET("videos")
    fun getVideos(): Call<List<VideoItem>>
}