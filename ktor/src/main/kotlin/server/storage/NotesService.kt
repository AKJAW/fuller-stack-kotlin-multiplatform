package server.storage

import com.soywiz.klock.DateTime
import kotlinx.coroutines.Dispatchers
import model.schema.NoteSchema
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync
import org.jetbrains.exposed.sql.update
import server.storage.model.NotesTable

//TODO coroutines
class NotesService(private val database: ExposedDatabase) {

    suspend fun getNotes(): List<NoteSchema> = queryDb {
        NotesTable.selectAll().map { row ->
            NoteSchema(
                id = row[NotesTable.id].value,
                title = row[NotesTable.title],
                content = row[NotesTable.content],
                creationDateTimestamp = row[NotesTable.creationDateTimestamp]
            )
        }
    }.await()


    suspend fun addNote(newNote: NoteSchema) = queryDb {
        NotesTable.insertAndGetId {
            it[title] = newNote.title
            it[content] = newNote.content
            it[creationDateTimestamp] = DateTime.nowUnixLong()
        }
        println(NotesTable.selectAll().count())
    }

    suspend fun updateNote(updatedNote: NoteSchema) = queryDb {
        NotesTable.update({ NotesTable.id eq updatedNote.id }) {
            it[title] = updatedNote.title
            it[content] = updatedNote.content
        }
    }

    private suspend fun <T> queryDb(block: suspend () -> T) = suspendedTransactionAsync(Dispatchers.IO) {
        block()
    }
}
