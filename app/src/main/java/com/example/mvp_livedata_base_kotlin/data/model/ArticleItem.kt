package com.example.mvp_livedata_base_kotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ArticleItem(
    val id: Int,
    val text: String,
    val title: String,
    val author_name: String,
    val url: String,
    val image_url: String,
    val premium: Int,
    val order: Int,
    val image: String
) : Parcelable