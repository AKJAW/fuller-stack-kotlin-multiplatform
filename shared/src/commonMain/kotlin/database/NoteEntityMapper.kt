package database

import model.Note
import model.NoteIdentifier

class NoteEntityMapper {

    fun toNotes(notes: List<NoteEntity>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteEntity): Note =
        Note(
            noteIdentifier = NoteIdentifier(note.noteId),
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp
        )

    fun toEntity(note: Note): NoteEntity =
        NoteEntity(
            noteId = note.noteIdentifier.id,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp
        )
}
