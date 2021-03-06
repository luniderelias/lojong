package com.example.mvp_livedata_base_kotlin.views.insight.articles

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.data.model.ArticleItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_insight_base.*

class ArticlesFragment : BaseFragment(), ArticlesContract.View {
    override val resLayout = R.layout.fragment_insight_base
    override val presenter by injectPresenter(this)

    private lateinit var adapter: ArticleItemsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setupRecyclerView()
        presenter.loadArticles()
    }

    override fun setViews() {
        emptyItemsTextView?.text = getString(R.string.articles_not_found)
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

    override fun onLoadArticlesError() {
        Snackbar.make(insightBaseCoordinatorLayout,getString(R.string.articles_load_failed), Snackbar.LENGTH_LONG)
            .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.design_default_color_error, null))
            .setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            .show()
    }

    override fun onLoadArticlesSuccessful(items: List<ArticleItem>) {
        updateAdapter(items)
    }

    override fun onShareButtonClick(articleItem: ArticleItem, position: Int) {
        val shareBody = articleItem.text + "\n" + articleItem.url
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

    override fun updateAdapter(items: List<ArticleItem>) {
        adapter.data = items
    }

    private fun setupRecyclerView(){
        activity?.let {
            adapter = ArticleItemsAdapter(this::onShareButtonClick)
            itemsRecyclerView?.adapter = adapter
            itemsRecyclerView?.layoutManager = LinearLayoutManager(it)
        }
    }
}