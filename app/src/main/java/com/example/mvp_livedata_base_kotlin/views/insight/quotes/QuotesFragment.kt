package com.example.mvp_livedata_base_kotlin.views.insight.quotes

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp_livedata_base_kotlin.BuildConfig
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.base.extensions.whenNull
import com.example.mvp_livedata_base_kotlin.data.model.QuoteItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_insight_base.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


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

    override fun onShareQuoteError() {
        Snackbar.make(insightBaseCoordinatorLayout,getString(R.string.quotes_sharing_failed), Snackbar.LENGTH_LONG)
            .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.design_default_color_error, null))
            .setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            .show()
    }

    override fun onLoadQuotesSuccessful(items: List<QuoteItem>) {
        updateAdapter(items)
    }

    override fun onShareButtonClick(bitmap: Bitmap?, position: Int) {
        bitmap?.let {
            saveImageToCacheStorage(it)
            shareImage()
        }.whenNull {
            onShareQuoteError()
        }
    }

    private fun saveImageToCacheStorage(bitmap: Bitmap){
        try {
            val cachePath = File(context!!.cacheDir, "images")
            cachePath.mkdirs()
            val stream = FileOutputStream("$cachePath/image.png")
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun shareImage(){
        val imagePath = File(context!!.cacheDir, "images")
        val newFile = File(imagePath, "image.png")
        val contentUri =
            FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".fileprovider", newFile)

        if (contentUri != null) {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            shareIntent.setDataAndType(contentUri, activity?.contentResolver?.getType(contentUri))
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
            startActivity(Intent.createChooser(shareIntent, "Choose an app"))
        }
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