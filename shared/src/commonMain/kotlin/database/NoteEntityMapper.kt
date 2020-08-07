package database

import com.soywiz.klock.DateTime
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.Note
import model.NoteIdentifier

class NoteEntityMapper {

    fun toNotes(notes: List<NoteEntity>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteEntity): Note =
        Note(
            noteIdentifier = NoteIdentifier(note.noteId),
            title = note.title,
            content = note.content,
            lastModificationDate = DateTime(note.lastModificationTimestamp.unix),
            creationDate = DateTime(note.creationTimestamp.unix)
        )

    fun toEntity(note: Note): NoteEntity =
        NoteEntity(
            noteId = note.noteIdentifier.id,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = LastModificationTimestamp(note.lastModificationDate.unixMillisLong),
            creationTimestamp = CreationTimestamp(note.creationDate.unixMillisLong)
        )
}
