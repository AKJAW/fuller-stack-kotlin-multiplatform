package network

import com.soywiz.klock.DateTime
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.Note
import model.NoteIdentifier

class NoteSchemaMapper {

    fun toNotes(notes: List<NoteSchema>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteSchema): Note =
        Note(
            noteIdentifier = NoteIdentifier(note.apiId),
            title = note.title,
            content = note.content,
            lastModificationDate = DateTime(note.lastModificationTimestamp.unix),
            creationDate = DateTime(note.creationTimestamp.unix)
        )

    fun toSchema(note: Note): NoteSchema =
        NoteSchema(
            apiId = note.noteIdentifier.id,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = LastModificationTimestamp(note.lastModificationDate.unixMillisLong),
            creationTimestamp = CreationTimestamp(note.creationDate.unixMillisLong)
        )
}
