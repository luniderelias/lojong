package com.example.mvp_livedata_base_kotlin.data.remote

import com.example.mvp_livedata_base_kotlin.base.extensions.awaitResult
import com.example.mvp_livedata_base_kotlin.data.model.*

class LojongRepositoryImpl(
    private val lojongApi: LojongApi
): LojongRepository {

    override suspend fun getVideos(): ServiceResponse<List<VideoItem>> {
        return lojongApi.getVideos().awaitResult()
    }

    override suspend fun getArticles(): ServiceResponse<ArticlesResponse> {
        return lojongApi.getArticles().awaitResult()
    }

    override suspend fun getQuotes(): ServiceResponse<List<QuoteItem>> {
        return lojongApi.getQuotes().awaitResult()
    }
}