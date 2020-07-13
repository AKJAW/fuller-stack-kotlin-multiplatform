package com.akjaw.fullerstack.notes.network

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNoteRequest(
    val id: Int,
    val title: String,
    val content: String
)
