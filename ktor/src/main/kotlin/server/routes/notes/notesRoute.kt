package server.routes.notes

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

fun Routing.notesRoute() {
    val apiLogger: ApiLogger by di().instance()
    val notesService: NotesService by di().instance()
    val notesCallHelper: NotesCallHelper by di().instance()

    get("/notes") {
        call.respond(notesService.getNotes())
    }

    post("/notes") {
        val note = notesCallHelper.getNoteSchemaFromBody(call)
        apiLogger.log("Post notes", note.toString())

        if (note == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        notesService.addNote(note)
        call.respond(HttpStatusCode.OK)
    }

    patch("/notes/{noteId}") {
        val note = notesCallHelper.getNoteWithId(call)
        apiLogger.log("Patch notes", note.toString())

        if (note == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@patch
        }

        val wasUpdated = notesService.updateNote(note)

        if(wasUpdated){
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    delete("/notes/{noteId}") {
        val noteId = call.parameters["noteId"]?.toIntOrNull()
        apiLogger.log("Delete note with id", noteId.toString())

        if (noteId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }

        val identifier = NoteIdentifier(noteId)
        val wasDeleted = notesService.deleteNote(identifier)

        if(wasDeleted){
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }

    delete("/notes") {
        val noteIdentifiers = notesCallHelper.getNoteIdentifiersFromBody(call)
        apiLogger.log("Delete notes", noteIdentifiers.toString())

        if (noteIdentifiers == null || noteIdentifiers.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }

        val wereAllNotesDeleted = noteIdentifiers.map { identifier ->
            notesService.deleteNote(identifier)
        }.all { it }

        if(wereAllNotesDeleted){
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
