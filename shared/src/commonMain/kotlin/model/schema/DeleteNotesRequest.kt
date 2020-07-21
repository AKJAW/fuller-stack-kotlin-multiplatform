package model.schema

import kotlinx.serialization.Serializable

@Serializable
data class DeleteNotesRequest(
    val noteIds: List<Int>//TODO make a data class
)
