package com.example.mvp_livedata_base_kotlin.views.insight.quotes

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.enums.QuoteEnum
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.data.model.QuoteItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_insight_base.*

class QuotesFragment : BaseFragment(), QuotesContract.View {
    override val resLayout = R.layout.fragment_insight_base

    override val presenter by injectPresenter(this)
    private lateinit var adapter: QuoteItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setupRecyclerView()
        presenter.loadQuotes()
    }

    override fun setViews() {
        emptyItemsTextView?.text = getString(R.string.quotes_not_found)
    }

    override fun handleItemsVisibility(shouldShowItems: Boolean) {
        itemsRecyclerView?.isVisible = shouldShowItems
        emptyItemsTextView?.isVisible = !shouldShowItems
    }

    override fun hideLoading() {
        progressBar.isVisible = false
    }

    override fun showLoading() {
        progressBar.isVisible = true
    }

    override fun onLoadQuotesError() {
        Snackbar.make(insightBaseCoordinatorLayout,getString(R.string.quotes_load_failed), Snackbar.LENGTH_LONG)
            .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.design_default_color_error, null))
            .setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            .show()
    }

    override fun onLoadQuotesSuccessful(items: List<QuoteItem>) {
        updateAdapter(items)
    }

    override fun onShareButtonClick(quoteItem: QuoteItem, position: Int) {
        val shareBody = quoteItem.text
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(
            Intent.createChooser(
                sharingIntent,
                getString(R.string.share_by)
            )
        )
    }

    override fun updateAdapter(items: List<QuoteItem>) {
        adapter.data = items
    }

    private fun setupRecyclerView(){
        activity?.let {
            adapter = QuoteItemsAdapter(it.applicationContext, this::onShareButtonClick)
            itemsRecyclerView?.adapter = adapter
            itemsRecyclerView?.layoutManager = LinearLayoutManager(it)
        }
    }
}