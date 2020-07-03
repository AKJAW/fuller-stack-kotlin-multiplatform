package server.storage

import com.soywiz.klock.DateTime
import model.schema.NoteSchema

@Suppress("MagicNumber")
class NotesStorage {
    private var notes = listOf(
        NoteSchema(
            id = 0,
            title = "Note 1",
            content = "Content of one",
            creationDateTimestamp = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
        ),
        NoteSchema(
            id = 1,
            title = "Note 2",
            content = "Content of one",
            creationDateTimestamp = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
        ),
        NoteSchema(
            id = 2,
            title = "Note 3",
            content = "Content of one",
            creationDateTimestamp = DateTime.createAdjusted(2020, 6, 5).unixMillisLong
        ),
        NoteSchema(
            id = 3,
            title = "Note 4",
            content = "Content of one",
            creationDateTimestamp = DateTime.createAdjusted(2020, 6, 7).unixMillisLong
        ),
        NoteSchema(
            id = 4,
            title = "Note 5",
            content = "Content of one",
            creationDateTimestamp = DateTime.createAdjusted(2020, 6, 8).unixMillisLong
        )
    )

    fun getNotes(): List<NoteSchema> = notes

    fun addNote(newNote: NoteSchema) {
        val lastId = notes.maxBy { it.id }?.id ?: -1
        notes = notes + newNote.copy(id = lastId + 1, creationDateTimestamp = DateTime.nowUnixLong())
    }

    fun updateNote(updatedNote: NoteSchema) {
        notes = notes.map { note ->
            if(updatedNote.id == note.id){
                note.copy(
                    title = updatedNote.title,
                    content = updatedNote.content
                )
            } else {
                note
            }
        }
    }
}
