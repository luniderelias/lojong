package com.example.mvp_livedata_base_kotlin.base.extensions

import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.circleCrop
import com.bumptech.glide.request.RequestOptions

val Boolean.shouldShowView: Int
    get() = if (this) View.VISIBLE
    else View.GONE

var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun ImageView.loadImage(imageUrl: String, circle: Boolean = false, errorPlaceholder: Int? = null) {

    val requestOptions = RequestOptions().apply {
        if (circle) circleCrop()
        if (errorPlaceholder != null) placeholder(AppCompatResources.getDrawable(context.applicationContext, errorPlaceholder))
    }

    Glide.with(context.applicationContext)
        .applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.DATA))
        .load(imageUrl)
        .apply(requestOptions)
        .into(this)
}
