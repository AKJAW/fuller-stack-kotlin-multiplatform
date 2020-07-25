package com.akjaw.fullerstack.screens.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import model.Note

@Parcelize
data class ParcelableNote(
    val id: Int,
    val title: String,
    val content: String
) : Parcelable

fun Note.toParcelable(): ParcelableNote { // TODO move to mapper
    return ParcelableNote(
        id = this.noteIdentifier.id,
        title = this.title,
        content = this.content
    )
}
