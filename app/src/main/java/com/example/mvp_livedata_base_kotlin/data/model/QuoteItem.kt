package com.example.mvp_livedata_base_kotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class QuoteItem(
    val id: Int,
    val text: String,
    val order: Int
) : Parcelable