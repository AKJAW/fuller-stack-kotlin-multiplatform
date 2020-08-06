package server.storage

import feature.AddNotePayload
import feature.UpdateNotePayload
import model.NoteIdentifier
import network.NoteSchema
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
                    apiId = row[NotesTable.id].value,
                    title = row[NotesTable.title],
                    content = row[NotesTable.content],
                    lastModificationTimestamp = row[NotesTable.lastModificationDateTimestamp],
                    creationTimestamp = row[NotesTable.creationDateTimestamp]
                )
            }
    }

    suspend fun addNote(payload: AddNotePayload): Int = queryDatabase {
        val id = NotesTable.insertAndGetId {
            it[title] = payload.title
            it[content] = payload.content
            it[lastModificationDateTimestamp] = payload.currentTimestamp
            it[creationDateTimestamp] = payload.currentTimestamp
        }
        apiLogger.log("NoteService addNote", "new id: $id")
        return@queryDatabase id.value
    }

    suspend fun updateNote(payload: UpdateNotePayload) = queryDatabase {
        val updatedAmount = NotesTable.update({ NotesTable.id eq payload.noteId }) {
            it[title] = payload.title
            it[content] = payload.content
            it[lastModificationDateTimestamp] = payload.lastModificationTimestamp
        }
        apiLogger.log("NoteService updateNote", "updatedAmount: $updatedAmount")
        return@queryDatabase updatedAmount > 0
    }

    suspend fun deleteNote(identifier: NoteIdentifier): Boolean = queryDatabase {
        val deletedAmount = NotesTable.deleteWhere { NotesTable.id eq identifier.id }
        apiLogger.log("NoteService deleteNote", "deletedAmount: $deletedAmount")
        return@queryDatabase deletedAmount > 0
    }
}
