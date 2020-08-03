package network

import feature.AddNotePayload
import feature.UpdateNotePayload
import io.ktor.client.HttpClient
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import model.Note
import model.NoteIdentifier
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

    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        return client.post(apiUrl) {
            body = json.write(addNotePayload)
        }
    }

    override suspend fun updateNote(updatedNotePayload: UpdateNotePayload) {
        client.patch<Unit>(apiUrl) {
            body = json.write(updatedNotePayload)
        }
    }

    override suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) {
        val ids = noteIdentifiers.map { it.id }
        if (ids.count() == 1) {
            client.delete<Unit>("$apiUrl/${ids.first()}")
        } else {
            client.delete<Unit>(apiUrl) {
                body = json.write(ids)
            }
        }
    }
}
