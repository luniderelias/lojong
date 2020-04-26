package com.example.mvp_livedata_base_kotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ArticlesResponse(
    val articles: List<ArticleItem>
) : Parcelable