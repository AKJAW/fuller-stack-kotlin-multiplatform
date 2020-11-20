package server.routes.notes

import feature.AddNotePayload
import feature.DeleteNotePayload
import feature.UpdateNotePayload
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.patch
import io.ktor.routing.post
import io.ktor.util.pipeline.PipelineContext
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.logger.ApiLogger
import server.routes.getJsonData
import server.routes.getUserId
import server.storage.NotesStorage
import server.storage.model.User

@Suppress("LongMethod")
fun Route.notesRoute() {
    val apiLogger: ApiLogger by di().instance()
    val notesStorage: NotesStorage by di().instance()

    get("/notes") {
        requireUser { user ->
            apiLogger.log("Auth userId", user.toString())
            call.respond(HttpStatusCode.OK, notesStorage.getNotes(user))
        }
    }

    post("/notes") {
        requireUser { user ->
            val payload = getJsonData<AddNotePayload>(call)
            apiLogger.log("Post notes", payload.toString())

            if (payload == null) {
                call.respond(HttpStatusCode.BadRequest, "Note doesn't have a correct format")
                return@requireUser
            }

            val addedId = notesStorage.addNote(payload, user)

            call.respond(HttpStatusCode.OK, addedId)
        }
    }

    patch("/notes") {
        requireUser { user ->
            val payload = getJsonData<UpdateNotePayload>(call)
            apiLogger.log("Patch notes", payload.toString())

            if (payload == null) {
                call.respond(HttpStatusCode.BadRequest, "Note doesn't have a correct format")
                return@requireUser
            }

            val wasUpdated = notesStorage.updateNote(payload, user)

            if (wasUpdated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Note from user $user with the provided timestamp ${payload.creationTimestamp.unix} not found"
                )
            }
        }
    }

    delete("/notes") {
        requireUser { user ->
            val deleteNotesPayloads = getJsonData<List<DeleteNotePayload>>(call)
            apiLogger.log("Delete notes", deleteNotesPayloads.toString())

            if (deleteNotesPayloads == null || deleteNotesPayloads.isEmpty()) {
                call.respond(HttpStatusCode.BadRequest, "No delete note payloads provided")
                return@requireUser
            }

            val wereAllNotesDeleted = deleteNotesPayloads.map { payload ->
                notesStorage.deleteNote(payload, user)
            }.all { it }

            if (wereAllNotesDeleted) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Some notes from user $user with the provided payload $deleteNotesPayloads not found"
                )
            }
        }
    }
}

private suspend inline fun PipelineContext<*, ApplicationCall>.requireUser(block: (userId: User) -> Unit) {
    val userId = call.getUserId()
    if (userId == null) {
        call.respond(HttpStatusCode.Unauthorized, "User id is null")
        return
    }
    val user = User(userId)
    block(user)
}
