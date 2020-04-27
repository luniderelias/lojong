package com.example.mvp_livedata_base_kotlin.views.insight.quotes

import com.example.mvp_livedata_base_kotlin.base.enums.QuoteEnum
import com.example.mvp_livedata_base_kotlin.base.extensions.launchIO
import com.example.mvp_livedata_base_kotlin.base.extensions.launchMain
import com.example.mvp_livedata_base_kotlin.data.model.ArticleItem
import com.example.mvp_livedata_base_kotlin.data.model.QuoteItem
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import com.example.mvp_livedata_base_kotlin.data.model.whenever
import com.example.mvp_livedata_base_kotlin.data.remote.LojongRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

class QuotesPresenter(
    override var view: QuotesContract.View,
    private val lojongRepository: LojongRepository
) : QuotesContract.Presenter {

    override fun init() {}

    override fun loadQuotes() {
        view.showLoading()
        launchIO {
            lojongRepository.getQuotes().whenever(
                isBody = { onLoadQuotesSuccessful(it) },
                isError = { onLoadQuotesError() },
                isEmptyBody = { onLoadQuotesError() },
                isOk = { onLoadQuotesError() }
            )
        }
    }

    override fun onLoadQuotesSuccessful(items: List<QuoteItem>) {
        launchMain {
            view.handleItemsVisibility(shouldShowItems = items.isNotEmpty())
            view.onLoadQuotesSuccessful(items)
            view.hideLoading()
        }
    }

    override fun onLoadQuotesError() {
        launchMain {
            view.handleItemsVisibility(shouldShowItems = false)
            view.onLoadQuotesError()
            view.hideLoading()
        }
    }
}