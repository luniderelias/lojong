package com.example.mvp_livedata_base_kotlin.views.insight.videos

import com.example.mvp_livedata_base_kotlin.base.BasePresenter
import com.example.mvp_livedata_base_kotlin.base.BaseView
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem

interface VideosContract {
    interface View : BaseView<Presenter> {
        fun setViews()
        fun showLoading()
        fun hideLoading()
        fun updateAdapter(items: List<VideoItem>)
        fun onShareButtonClick(videoItem: VideoItem, position: Int)
        fun onPlayVideoClick(videoItem: VideoItem, position: Int)
        fun onLoadVideosSuccessful(items: List<VideoItem>)
        fun onLoadVideosError()
        fun handleItemsVisibility(shouldShowItems: Boolean)
    }
    interface Presenter : BasePresenter<View> {
        fun init()
        fun loadVideos()
        fun onLoadVideosSuccessful(items: List<VideoItem>)
        fun onLoadVideosError()
    }
}