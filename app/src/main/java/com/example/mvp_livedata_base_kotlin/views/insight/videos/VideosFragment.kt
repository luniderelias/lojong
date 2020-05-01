package com.example.mvp_livedata_base_kotlin.views.insight.videos

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseFragment
import com.example.mvp_livedata_base_kotlin.base.extensions.injectPresenter
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import com.example.mvp_livedata_base_kotlin.views.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_insight_base.*

class VideosFragment : BaseFragment(), VideosContract.View {
    override val resLayout = R.layout.fragment_insight_base
    override val presenter by injectPresenter(this)

    private lateinit var adapter: VideoItemsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setupRecyclerView()
        presenter.loadVideos()
    }

    override fun setViews() {
        emptyItemsTextView?.text = getString(R.string.videos_not_found)
    }

    override fun onLoadVideosSuccessful(items: List<VideoItem>) {
        updateAdapter(items)
    }

    override fun handleItemsVisibility(shouldShowItems: Boolean) {
        itemsRecyclerView?.isVisible = shouldShowItems
        emptyItemsTextView?.isVisible = !shouldShowItems
    }

    override fun onLoadVideosError() {
        Snackbar.make(
            insightBaseCoordinatorLayout,
            getString(R.string.videos_load_failed),
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(
            ResourcesCompat.getColor(
                resources,
                R.color.design_default_color_error,
                null
            )
        ).setTextColor(ResourcesCompat.getColor(resources, R.color.white, null)).show()
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

    override fun onShareButtonClick(videoItem: VideoItem, position: Int) {
        val shareBody = videoItem.description + "\n" + videoItem.url2
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

    override fun onPlayVideoClick(videoItem: VideoItem, position: Int) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(videoItem.aws_url), "video/*")
        activity?.let {
            try {
                ContextCompat.startActivity(it as MainActivity, intent, null)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(
                    it,
                    getString(R.string.play_video_without_player_error_message),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun setupRecyclerView() {
        activity?.let {
            adapter = VideoItemsAdapter(this::onPlayVideoClick, this::onShareButtonClick)
            itemsRecyclerView?.adapter = adapter
            itemsRecyclerView?.layoutManager = LinearLayoutManager(it)
        }
    }
}