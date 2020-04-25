package com.example.mvp_livedata_base_kotlin.views.insight.videos

import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_videos.*

class VideosFragment : BaseFragment(), VideosContract.View {
    override val resLayout = R.layout.fragment_videos
    override val presenter by injectPresenter(this)

    private var adapter = VideoItemsAdapter(this::onShareButtonClick, this::onPlayVideoClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        presenter.loadVideos()
    }

    override fun onLoadVideosSuccessful(items: List<VideoItem>) {
        updateAdapter(items)
    }

    override fun handleItemsVisibility(shouldShowItems: Boolean) {
        videosRecyclerView?.isVisible = shouldShowItems
        noVideosTextView?.isVisible = !shouldShowItems
    }

    override fun onLoadVideosError() {
        Snackbar.make(videosCoordinatorLayout,getString(R.string.videos_load_failed),Snackbar.LENGTH_LONG)
            .setBackgroundTint(ResourcesCompat.getColor(resources, R.color.design_default_color_error, null))
            .setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
            .show()
    }

    override fun showLoading() {
        progressBar.isVisible = true
    }

    override fun hideLoading() {
        progressBar.isVisible = false
    }

    override fun updateAdapter(items: List<VideoItem>) {
        adapter.data = items
    }

    override fun onShareButtonClick(videoItem: VideoItem, position: Int){

    }

    override fun onPlayVideoClick(videoItem: VideoItem, position: Int){

    }

    private fun setupRecyclerView(){
        videosRecyclerView?.adapter = adapter
        videosRecyclerView?.layoutManager = LinearLayoutManager(activity)
    }
}