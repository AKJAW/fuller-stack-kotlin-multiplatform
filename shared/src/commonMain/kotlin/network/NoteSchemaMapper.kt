package network

import com.soywiz.klock.DateTime
import model.Note
import model.NoteIdentifier
import model.schema.NoteSchema

class NoteSchemaMapper {

    fun toNotes(notes: List<NoteSchema>): List<Note> = notes.map(::toNote)

    fun toNote(note: NoteSchema): Note =
        Note(
            noteIdentifier = NoteIdentifier(note.id),
            title = note.title,
            content = note.content,
            lastModificationDate = DateTime(note.lastModificationTimestamp),
            creationDate = DateTime(note.creationTimestamp)
        )
}
