package network

import model.Note

class NoteSchemaMapper {

    fun toNotes(notes: List<NoteSchema>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteSchema): Note =
        Note(
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp,
            hasSyncFailed = false
        )
}
