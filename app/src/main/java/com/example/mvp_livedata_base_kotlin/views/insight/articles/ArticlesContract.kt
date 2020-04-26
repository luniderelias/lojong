package com.example.mvp_livedata_base_kotlin.views.insight.articles

import com.example.mvp_livedata_base_kotlin.base.BasePresenter
import com.example.mvp_livedata_base_kotlin.base.BaseView
import com.example.mvp_livedata_base_kotlin.data.model.ArticleItem
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem

interface ArticlesContract {
    interface View : BaseView<Presenter> {
        fun setViews()
        fun showLoading()
        fun hideLoading()
        fun updateAdapter(items: List<ArticleItem>)
        fun onShareButtonClick(articleItem: ArticleItem, position: Int)
        fun onLoadArticlesSuccessful(items: List<ArticleItem>)
        fun onLoadArticlesError()
        fun handleItemsVisibility(shouldShowItems: Boolean)
    }
    interface Presenter : BasePresenter<View> {
        fun init()
        fun loadArticles()
        fun onLoadArticlesSuccessful(items: List<ArticleItem>)
        fun onLoadArticlesError()
    }
}