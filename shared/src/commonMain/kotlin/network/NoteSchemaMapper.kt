package network

import com.soywiz.klock.DateTime
import model.Note
import model.NoteIdentifier

class NoteSchemaMapper {

    fun toNotes(notes: List<NoteSchema>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteSchema): Note =
        Note(
            noteIdentifier = NoteIdentifier(note.apiId),
            title = note.title,
            content = note.content,
            lastModificationDate = DateTime(note.lastModificationTimestamp),
            creationDate = DateTime(note.creationTimestamp)
        )

    fun toSchema(note: Note): NoteSchema =
        NoteSchema(
            apiId = note.noteIdentifier.id,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationDate.unixMillisLong,
            creationTimestamp = note.creationDate.unixMillisLong
        )
}
