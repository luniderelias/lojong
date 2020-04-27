package com.example.mvp_livedata_base_kotlin.views.insight.quotes

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.example.mvp_livedata_base_kotlin.R
import com.example.mvp_livedata_base_kotlin.base.BaseAdapter
import com.example.mvp_livedata_base_kotlin.base.enums.QuoteEnum
import com.example.mvp_livedata_base_kotlin.base.extensions.isVisible
import com.example.mvp_livedata_base_kotlin.data.model.QuoteItem
import kotlinx.android.synthetic.main.item_quote.view.*


class QuoteItemsAdapter(
    private val context: Context,
    private val shareAction: (Bitmap?, Int) -> Unit
) : BaseAdapter<QuoteItem>() {

    var quoteEnum: QuoteEnum = QuoteEnum.FIRST_QUOTE

    override val layoutResource = R.layout.item_quote

    override fun bind(itemView: View, item: QuoteItem, position: Int) {
        val quoteType = quoteEnum

        item.splitAuthorName()
        itemView.quoteDescription.text = item.text
        itemView.quoteAuthor.text = item.authorName
        itemView.quoteImage.setImageResource(getBackgroundDrawable(quoteType))
        val textColor = getTextColor(quoteType)

        itemView.quoteDescription.setTextColor(textColor)
        itemView.quoteAuthor.setTextColor(textColor)

        itemView.shareQuoteButton.background =
            AppCompatResources.getDrawable(context, getShareBackgroundDrawable(quoteType))
        itemView.shareQuoteButton.setOnClickListener {
            shareAction.invoke(getBitmapFromView(itemView), position)
        }

        switchQuoteEnum(quoteType)
    }

    private fun getBitmapFromView(view: View): Bitmap? {
        view.quoteConstraintLayout.shareQuoteButton.isVisible = false
        val returnedBitmap =
            Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        view.quoteConstraintLayout.shareQuoteButton.isVisible = true
        return returnedBitmap
    }

    private fun getTextColor(quoteEnum: QuoteEnum): Int {
        return ContextCompat.getColor(
            context,
            when (quoteEnum) {
                QuoteEnum.FIRST_QUOTE -> R.color.quote_blue_text
                QuoteEnum.SECOND_QUOTE -> R.color.quote_grey_text
                QuoteEnum.THIRD_QUOTE -> R.color.white
            }
        )
    }

    private fun getBackgroundDrawable(quoteEnum: QuoteEnum): Int {
        return when (quoteEnum) {
            QuoteEnum.FIRST_QUOTE -> R.drawable.first_quote_background
            QuoteEnum.SECOND_QUOTE -> R.drawable.second_quote_background
            QuoteEnum.THIRD_QUOTE -> R.drawable.third_quote_background
        }
    }

    private fun getShareBackgroundDrawable(quoteEnum: QuoteEnum): Int {
        return when (quoteEnum) {
            QuoteEnum.FIRST_QUOTE -> R.drawable.round_blue_background
            QuoteEnum.SECOND_QUOTE -> R.drawable.round_transparent_background
            QuoteEnum.THIRD_QUOTE -> R.drawable.round_transparent_background
        }
    }

    private fun switchQuoteEnum(quoteType: QuoteEnum) {
        quoteEnum = when (quoteType) {
            QuoteEnum.FIRST_QUOTE -> QuoteEnum.SECOND_QUOTE
            QuoteEnum.SECOND_QUOTE -> QuoteEnum.THIRD_QUOTE
            QuoteEnum.THIRD_QUOTE -> QuoteEnum.FIRST_QUOTE
        }
    }
}