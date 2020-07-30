package feature

import model.NoteIdentifier

data class UpdateNotePayload(
    val noteIdentifier: NoteIdentifier,
    val title: String,
    val content: String,
    val lastModificationTimestamp: Long
)
