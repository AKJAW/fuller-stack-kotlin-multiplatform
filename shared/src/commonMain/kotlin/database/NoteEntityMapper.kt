package database

import com.soywiz.klock.DateTime
import model.Note
import model.NoteIdentifier

class NoteEntityMapper {

    fun toNotes(notes: List<NoteEntity>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteEntity): Note =
        Note(
            noteIdentifier = NoteIdentifier(note.noteId),
            title = note.title,
            content = note.content,
            lastModificationDate = DateTime(note.lastModificationTimestamp),
            creationDate = DateTime(note.creationTimestamp)
        )

    fun toEntity(note: Note): NoteEntity =
        NoteEntity(
            noteId = note.noteIdentifier.id,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationDate.unixMillisLong,
            creationTimestamp = note.creationDate.unixMillisLong
        )
}
