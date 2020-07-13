package server.storage

import com.soywiz.klock.DateTime
import model.schema.NoteSchema
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import server.storage.model.NotesTable

class NotesService(private val database: ExposedDatabase) {

    suspend fun getNotes(): List<NoteSchema> = queryDatabase {
        NotesTable.selectAll().map { row ->
            NoteSchema(
                id = row[NotesTable.id].value,
                title = row[NotesTable.title],
                content = row[NotesTable.content],
                creationDateTimestamp = row[NotesTable.creationDateTimestamp]
            )
        }
    }

    suspend fun addNote(newNote: NoteSchema) = queryDatabase {
        NotesTable.insertAndGetId {
            it[title] = newNote.title
            it[content] = newNote.content
            it[creationDateTimestamp] = DateTime.nowUnixLong()
        }
        println(NotesTable.selectAll().count())
    }

    suspend fun updateNote(updatedNote: NoteSchema) = queryDatabase {
        NotesTable.update({ NotesTable.id eq updatedNote.id }) {
            it[title] = updatedNote.title
            it[content] = updatedNote.content
        }
    }
}
