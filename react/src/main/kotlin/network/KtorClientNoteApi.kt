package network

import feature.AddNotePayload
import feature.UpdateNotePayload
import io.ktor.client.HttpClient
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import model.schema.NoteSchema

class KtorClientNoteApi(
    private val client: HttpClient
) : NoteApi {

    val apiUrl = "https://fuller-stack-ktor.herokuapp.com/notes"
    val json = KotlinxSerializer()

    override suspend fun getNotes(): List<NoteSchema> {
        return client.get(apiUrl)
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

    override suspend fun deleteNotes(ids: List<Int>) {
        client.delete<Unit>(apiUrl) {
            body = json.write(ids)
        }
    }
}
