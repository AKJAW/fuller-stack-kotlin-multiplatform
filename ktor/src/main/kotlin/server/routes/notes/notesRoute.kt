package server.routes.notes

import feature.AddNotePayload
import feature.UpdateNotePayload
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.patch
import io.ktor.routing.post
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.logger.ApiLogger
import server.storage.NotesService

@Suppress("LongMethod")
fun Routing.notesRoute() {
    val apiLogger: ApiLogger by di().instance()
    val notesService: NotesService by di().instance()
    val notesCallHelper: NotesCallHelper by di().instance()

    get("/notes") {
        call.respond(HttpStatusCode.OK, notesService.getNotes())
    }

    post("/notes") {
        val payload = notesCallHelper.getJsonData<AddNotePayload>(call)
        apiLogger.log("Post notes", payload.toString())

        if (payload == null) {
            call.respond(HttpStatusCode.BadRequest, "Note doesn't have a correct format")
            return@post
        }

        val addedId = notesService.addNote(payload)

        call.respond(HttpStatusCode.OK, addedId)
    }

    patch("/notes") {
        val payload = notesCallHelper.getJsonData<UpdateNotePayload>(call)
        apiLogger.log("Patch notes", payload.toString())

        if (payload == null) {
            call.respond(HttpStatusCode.BadRequest, "Note doesn't have a correct format")
            return@patch
        }

        val wasUpdated = notesService.updateNote(payload)

        if (wasUpdated) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(
                HttpStatusCode.BadRequest,
                "Note with provided timestamp ${payload.creationTimestamp.unix} not found"
            )
        }
    }

    delete("/notes") {
        val timestamps = notesCallHelper.getCreationTimestampsFromBody(call)
        apiLogger.log("Delete notes", timestamps.toString())

        if (timestamps == null || timestamps.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest, "No note timestamps provided")
            return@delete
        }

        val wereAllNotesDeleted = timestamps.map { timestamp ->
            notesService.deleteNote(timestamp)
        }.all { it }

        if (wereAllNotesDeleted) {
            call.respond(HttpStatusCode.OK)
        } else {
            val unixTimestamps = timestamps.map { it.unix }
            call.respond(HttpStatusCode.BadRequest, "Some notes with provided timestamps $unixTimestamps not found")
        }
    }
}
