package server.routes

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveOrNull
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import kotlinx.serialization.json.JsonDecodingException
import model.schema.NoteSchema
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.storage.NotesStorage

fun Routing.updateNotePostRoute(){
    val notesStorage: NotesStorage by di().instance<NotesStorage>()

    post("/update-note") {
        val note = getNoteSchemaFromBody(call)
        println(note)

        if(note == null || note.id < 0){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        call.respond(HttpStatusCode.OK)
        notesStorage.updateNote(note)
    }
}

private suspend fun getNoteSchemaFromBody(call: ApplicationCall): NoteSchema? =
    try {
        call.receiveOrNull()
    } catch (e: JsonDecodingException) {
        null
    }
