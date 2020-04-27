package com.example.mvp_livedata_base_kotlin.data.remote

import com.example.mvp_livedata_base_kotlin.data.model.ArticlesResponse
import com.example.mvp_livedata_base_kotlin.data.model.QuoteItem
import com.example.mvp_livedata_base_kotlin.data.model.ServiceResponse
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem

interface LojongRepository {
    suspend fun getVideos(): ServiceResponse<List<VideoItem>>
    suspend fun getArticles(): ServiceResponse<ArticlesResponse>
    suspend fun getQuotes(): ServiceResponse<List<QuoteItem>>
}