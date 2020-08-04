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
import model.NoteIdentifier
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
            call.respond(HttpStatusCode.BadRequest, "Note with provided id ${payload.noteId} not found")
        }
    }

    delete("/notes/{noteId}") {
        val noteId = call.parameters["noteId"]?.toIntOrNull()
        apiLogger.log("Delete note with id", noteId.toString())

        if (noteId == null) {
            call.respond(HttpStatusCode.BadRequest, "Note id is missing")
            return@delete
        }

        val identifier = NoteIdentifier(noteId)
        val wasDeleted = notesService.deleteNote(identifier)

        if (wasDeleted) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest, "Note with provided id $noteId not found")
        }
    }

    delete("/notes") {
        val noteIdentifiers = notesCallHelper.getNoteIdentifiersFromBody(call)
        apiLogger.log("Delete notes", noteIdentifiers.toString())

        if (noteIdentifiers == null || noteIdentifiers.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest, "No note id provided")
            return@delete
        }

        val wereAllNotesDeleted = noteIdentifiers.map { identifier ->
            notesService.deleteNote(identifier)
        }.all { it }

        if (wereAllNotesDeleted) {
            call.respond(HttpStatusCode.OK)
        } else {
            val ids = noteIdentifiers.map { it.id }
            call.respond(HttpStatusCode.BadRequest, "Some notes with provided ids $ids not found")
        }
    }
}
