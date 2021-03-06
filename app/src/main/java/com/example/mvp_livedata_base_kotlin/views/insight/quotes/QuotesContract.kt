package com.example.mvp_livedata_base_kotlin.views.insight.quotes

import android.graphics.Bitmap
import com.example.mvp_livedata_base_kotlin.base.BasePresenter
import com.example.mvp_livedata_base_kotlin.base.BaseView
import com.example.mvp_livedata_base_kotlin.data.model.ArticleItem
import com.example.mvp_livedata_base_kotlin.data.model.QuoteItem
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem

interface QuotesContract {
    interface View : BaseView<Presenter> {
        fun setViews()
        fun showLoading()
        fun hideLoading()
        fun updateAdapter(items: List<QuoteItem>)
        fun onShareButtonClick(bitmap: Bitmap?, position: Int)
        fun onLoadQuotesSuccessful(items: List<QuoteItem>)
        fun onLoadQuotesError()
        fun onShareQuoteError()
        fun handleItemsVisibility(shouldShowItems: Boolean)
    }
    interface Presenter : BasePresenter<View> {
        fun init()
        fun loadQuotes()
        fun onLoadQuotesSuccessful(items: List<QuoteItem>)
        fun onLoadQuotesError()
    }
}