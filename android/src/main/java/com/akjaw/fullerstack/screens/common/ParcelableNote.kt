package com.akjaw.fullerstack.screens.common

import android.os.Parcelable
import data.Note
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ParcelableNote(
    val id: Int,
    val title: String,
    val content: String
) : Parcelable

fun Note.toParcelable(): ParcelableNote { //TODO move to mapper
    return ParcelableNote(
        id = this.id,
        title = this.title,
        content = this.content
    )
}
