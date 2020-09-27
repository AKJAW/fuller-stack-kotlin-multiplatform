package network

import feature.AddNotePayload
import feature.DeleteNotePayload
import feature.UpdateNotePayload
import io.ktor.client.HttpClient
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import model.CreationTimestamp
import model.toLastModificationTimestamp

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

    override suspend fun deleteNotes(
        creationTimestamps: List<CreationTimestamp>,
        lastModificationTimestamp: Long
    ) {
        client.delete<Unit>(apiUrl) {
            body = json.write(
                creationTimestamps.createDeleteNotePayloads(
                    wasDeleted = true,
                    lastModificationTimestamp = lastModificationTimestamp
                )
            )
        }
    }

    override suspend fun restoreNotes(
        creationTimestamps: List<CreationTimestamp>,
        lastModificationTimestamp: Long
    ) {
        client.delete<Unit>(apiUrl) {
            body = json.write(
                creationTimestamps.createDeleteNotePayloads(
                    wasDeleted = false,
                    lastModificationTimestamp = lastModificationTimestamp
                )
            )
        }
    }

    private fun List<CreationTimestamp>.createDeleteNotePayloads(
        wasDeleted: Boolean,
        lastModificationTimestamp: Long
    ): List<DeleteNotePayload> {
        return this.map { creationTimestamp ->
            DeleteNotePayload(
                creationTimestamp = creationTimestamp,
                wasDeleted = wasDeleted,
                lastModificationTimestamp = lastModificationTimestamp.toLastModificationTimestamp()
            )
        }
    }
}
