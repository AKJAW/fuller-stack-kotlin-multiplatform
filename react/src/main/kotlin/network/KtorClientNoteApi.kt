package network

import io.ktor.client.HttpClient
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.url
import model.Note
import model.schema.NoteRequest
import model.schema.NoteSchema

class KtorClientNoteApi(
    private val client: HttpClient,
    private val noteSchemaMapper: NoteSchemaMapper
) : NoteApi {

    val apiUrl = "https://fuller-stack-ktor.herokuapp.com/notes"
    val json = KotlinxSerializer()

    override suspend fun getNotes(): List<Note> {
        val schemas: List<NoteSchema> = client.get(apiUrl)
        return noteSchemaMapper.toNotes(schemas)
    }

    override suspend fun addNote(newNote: Note) {
        client.post<Unit> {
            url(apiUrl)
            val request = NoteRequest(
                title = newNote.title,
                content = newNote.content
            )
            body = json.write(request)
        }
    }

    override suspend fun updateNote(updatedNote: Note) {
        client.patch<Unit> {
            url("$apiUrl/${updatedNote.id}")
            val request = NoteRequest(
                title = updatedNote.title,
                content = updatedNote.content
            )
            body = json.write(request)
        }
    }

    override suspend fun deleteNotes(noteIds: List<Int>) {
        noteIds.forEach { noteId ->
            client.delete("$apiUrl/${noteId}")
        }
    }
}
