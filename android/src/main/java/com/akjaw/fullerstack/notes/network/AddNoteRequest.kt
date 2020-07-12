package com.akjaw.fullerstack.notes.network

import kotlinx.serialization.Serializable

@Serializable
data class AddNoteRequest(
    val title: String,
    val content: String
)
