package com.example.mvp_livedata_base_kotlin.data.remote

import com.example.mvp_livedata_base_kotlin.base.extensions.awaitResult
import com.example.mvp_livedata_base_kotlin.data.model.ServiceResponse
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem

class LojongRepositoryImpl(
    private val lojongApi: LojongApi
): LojongRepository {

    override suspend fun getVideos(): ServiceResponse<List<VideoItem>> {
        return lojongApi.getVideos().awaitResult()
    }
}