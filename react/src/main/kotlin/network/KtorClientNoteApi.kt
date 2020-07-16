package network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import model.Note
import model.schema.NoteSchema

class KtorClientNoteApi(
    private val client: HttpClient,
    private val noteSchemaMapper: NoteSchemaMapper
) : NoteApi {

    val baseUrl = "https://fuller-stack-ktor.herokuapp.com"

    override suspend fun getNotes(): List<Note> {
        val schemas: List<NoteSchema> = client.get("$baseUrl/notes")
        console.log(schemas)
        return noteSchemaMapper.toNotes(schemas)
    }

    override suspend fun addNote(newNote: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNote(updatedNote: Note) {
        TODO("Not yet implemented")
    }
}
