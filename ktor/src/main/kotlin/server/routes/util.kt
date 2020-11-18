package server.routes

import io.ktor.application.ApplicationCall
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.principal
import io.ktor.request.receiveOrNull
import kotlinx.serialization.SerializationException

suspend inline fun <reified T : Any> getJsonData(call: ApplicationCall): T? =
    try {
        call.receiveOrNull()
    } catch (e: SerializationException) {
        null
    }

suspend fun ApplicationCall.getUserId(): String? {
    val jwtPrincipal = principal<JWTPrincipal>()
    return jwtPrincipal?.payload?.getClaim("sub")?.asString()
}
