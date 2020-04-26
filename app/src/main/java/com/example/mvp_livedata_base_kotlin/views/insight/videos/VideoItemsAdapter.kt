package com.example.mvp_livedata_base_kotlin.views.insight.videos

import android.view.View
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseAdapter
import com.example.mvp_livedata_base_kotlin.base.extensions.loadImage
import com.example.mvp_livedata_base_kotlin.data.model.VideoItem
import kotlinx.android.synthetic.main.item_video.view.*

class VideoItemsAdapter(
    private val playVideoAction: (VideoItem, Int) -> Unit,
    private val shareAction: (VideoItem, Int) -> Unit): BaseAdapter<VideoItem>() {

    override val layoutResource = R.layout.item_video

    override fun bind(itemView: View, item: VideoItem, position: Int) {
        itemView.videoTitle.text = item.name
        itemView.videoImage.loadImage(item.image_url, false, R.drawable.ic_elephants)
        itemView.playVideoButton.setOnClickListener { playVideoAction.invoke(item, position) }
        itemView.videoDescription.text = item.description
        itemView.shareVideoButton.setOnClickListener { shareAction.invoke(item, position) }
    }

}