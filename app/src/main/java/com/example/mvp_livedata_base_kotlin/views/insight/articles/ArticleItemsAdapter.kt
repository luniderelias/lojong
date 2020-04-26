package com.example.mvp_livedata_base_kotlin.views.insight.articles

import android.view.View
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseAdapter
import com.example.mvp_livedata_base_kotlin.base.extensions.loadImage
import com.example.mvp_livedata_base_kotlin.data.model.ArticleItem
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleItemsAdapter(
    private val shareAction: (ArticleItem, Int) -> Unit): BaseAdapter<ArticleItem>() {

    override val layoutResource = R.layout.item_article

    override fun bind(itemView: View, item: ArticleItem, position: Int) {
        itemView.articleTitle.text = item.title
        itemView.articleImage.loadImage(item.image_url, false, R.drawable.ic_elephants)
        itemView.articleDescription.text = item.text
        itemView.shareArticleButton.setOnClickListener { shareAction.invoke(item, position) }
    }

}