package com.example.mvp_livedata_base_kotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class VideoItem(
    val id: Int,
    val name: String,
    val description: String,
    val file: String,
    val url: String,
    val url2: String,
    val aws_url: String,
    val image: String,
    val image_url: String,
    val premium: Int,
    val order: Int
) : Parcelable {

}