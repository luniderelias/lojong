package com.example.mvp_livedata_base_kotlin.views.insight.articles

import com.example.mvp_livedata_base_kotlin.base.extensions.launchIO
import com.example.mvp_livedata_base_kotlin.base.extensions.launchMain
import com.example.mvp_livedata_base_kotlin.data.model.ArticleItem
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import com.example.mvp_livedata_base_kotlin.data.model.whenever
import com.example.mvp_livedata_base_kotlin.data.remote.LojongRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

class ArticlesPresenter(
    override var view: ArticlesContract.View,
    private val lojongRepository: LojongRepository
) : ArticlesContract.Presenter {

    override fun init() {}

    override fun loadArticles() {
        view.showLoading()
        launchIO {
            lojongRepository.getArticles().whenever(
                isBody = { onLoadArticlesSuccessful(it.articles) },
                isError = { onLoadArticlesError() },
                isEmptyBody = { onLoadArticlesError() },
                isOk = { onLoadArticlesError() }
            )
        }
    }

    override fun onLoadArticlesSuccessful(items: List<ArticleItem>) {
        launchMain {
            view.handleItemsVisibility(shouldShowItems = items.isNotEmpty())
            view.onLoadArticlesSuccessful(items)
            view.hideLoading()
        }
    }

    override fun onLoadArticlesError() {
        launchMain {
            view.handleItemsVisibility(shouldShowItems = false)
            view.onLoadArticlesError()
            view.hideLoading()
        }
    }
}