package com.example.mvp_livedata_base_kotlin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import java.lang.IndexOutOfBoundsException

@Parcelize
class QuoteItem(
    val id: Int,
    var text: String,
    val order: Int
) : Parcelable{

    @IgnoredOnParcel
    var authorName: String = ""

    fun splitAuthorName() {
        try {
            val splitText = text.split("&nbsp;&ndash;&nbsp;")
            text = splitText[0]
            authorName = splitText[1]
        }catch (e: IndexOutOfBoundsException){
            e.printStackTrace()
            authorName = "Unknown"
        }
    }
}