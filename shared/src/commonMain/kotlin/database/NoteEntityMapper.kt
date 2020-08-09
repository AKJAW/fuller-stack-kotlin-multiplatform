package database

import model.Note

class NoteEntityMapper {

    fun toNotes(notes: List<NoteEntity>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteEntity): Note =
        Note(
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp
        )
}
