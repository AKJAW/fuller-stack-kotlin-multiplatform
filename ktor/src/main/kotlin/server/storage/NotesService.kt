package server.storage

import com.soywiz.klock.DateTime
import model.schema.NoteSchema
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import server.logger.ApiLogger
import server.storage.model.NotesTable

class NotesService(
    private val database: ExposedDatabase,
    private val apiLogger: ApiLogger
) {

    suspend fun getNotes(): List<NoteSchema> = queryDatabase {
        NotesTable
            .selectAll()
            .orderBy(NotesTable.creationDateTimestamp to SortOrder.DESC)
            .map { row ->
                NoteSchema(
                    id = row[NotesTable.id].value,
                    title = row[NotesTable.title],
                    content = row[NotesTable.content],
                    creationDateTimestamp = row[NotesTable.creationDateTimestamp]
                )
            }
    }

    suspend fun addNote(newNote: NoteSchema) = queryDatabase {
        val id = NotesTable.insertAndGetId {
            it[title] = newNote.title
            it[content] = newNote.content
            it[creationDateTimestamp] = DateTime.nowUnixLong()
        }
        apiLogger.log("NoteService addNote", "new id: $id")
    }

    suspend fun updateNote(updatedNote: NoteSchema) = queryDatabase {
        val updatedAmount = NotesTable.update({ NotesTable.id eq updatedNote.id }) {
            it[title] = updatedNote.title
            it[content] = updatedNote.content
        }
        apiLogger.log("NoteService updateNote", "updatedAmount: $updatedAmount")
        return@queryDatabase updatedAmount > 0
    }

    suspend fun deleteNote(noteId: Int): Boolean = queryDatabase {
        val deletedAmount = NotesTable.deleteWhere { NotesTable.id eq noteId }
        apiLogger.log("NoteService deleteNote", "deletedAmount: $deletedAmount")
        return@queryDatabase deletedAmount > 0
    }
}
