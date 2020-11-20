package server.routes

import io.ktor.application.ApplicationCall
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.principal
import io.ktor.request.receiveOrNull
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import kotlinx.serialization.SerializationException
import server.NotesSession

suspend inline fun <reified T : Any> getJsonData(call: ApplicationCall): T? =
    try {
        call.receiveOrNull()
    } catch (e: SerializationException) {
        null
    }

fun ApplicationCall.getUserId(): String? {
    val jwtPrincipal = principal<JWTPrincipal>()
    return jwtPrincipal?.payload?.getClaim("sub")?.asString()
}

fun ApplicationCall.getSessionId(): String? {
    return sessions.get<NotesSession>()?.id
}
