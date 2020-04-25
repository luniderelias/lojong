package com.example.mvp_livedata_base_kotlin.views.insight.videos

import com.example.mvp_livedata_base_kotlin.base.extensions.launchIO
import com.example.mvp_livedata_base_kotlin.base.extensions.launchMain
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import com.example.mvp_livedata_base_kotlin.data.model.whenever
import com.example.mvp_livedata_base_kotlin.data.remote.LojongRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

class VideosPresenter(
    override var view: VideosContract.View,
    private val lojongRepository: LojongRepository,
    private val dispatcherContext: CoroutineDispatcher
) : VideosContract.Presenter {

    override fun init() {}

    override fun loadVideos() {
        view.showLoading()
        launchIO {
            lojongRepository.getVideos().whenever(
                isBody = { onLoadVideosSuccessful(it) },
                isError = { onLoadVideosError() },
                isEmptyBody = { onLoadVideosError() },
                isOk = { onLoadVideosError() }
            )
        }
    }

    override fun onLoadVideosSuccessful(items: List<VideoItem>) {
        launchMain {
            view.handleItemsVisibility(shouldShowItems = items.isNotEmpty())
            view.onLoadVideosSuccessful(items)
            view.hideLoading()
        }
    }

    override fun onLoadVideosError() {
        launchMain {
            view.handleItemsVisibility(shouldShowItems = false)
            view.onLoadVideosError()
            view.hideLoading()
        }
    }
}