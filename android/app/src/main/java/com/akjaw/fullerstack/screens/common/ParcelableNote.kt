package com.akjaw.fullerstack.screens.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import model.Note

@Parcelize
data class ParcelableNote(
    val creationUnixTimestamp: Long,
    val title: String,
    val content: String
) : Parcelable

fun Note.toParcelable(): ParcelableNote {
    return ParcelableNote(
        creationUnixTimestamp = this.creationTimestamp.unix,
        title = this.title,
        content = this.content
    )
}
