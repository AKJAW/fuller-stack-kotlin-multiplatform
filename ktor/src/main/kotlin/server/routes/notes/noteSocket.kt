package server.routes.notes

import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.close
import io.ktor.routing.Route
import io.ktor.sessions.get
import io.ktor.sessions.sessions
import io.ktor.websocket.WebSocketServerSession
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.consume
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.NotesSession
import server.logger.ApiLogger
import server.routes.getUserId
import server.storage.model.User


fun Route.notesSocket() {
    val apiLogger: ApiLogger by di().instance()

    webSocket("/notes/ws") {
        val user = getUser() ?: return@webSocket
        apiLogger.log("Socket user", user.toString())

        val session = getSession() ?: return@webSocket
        apiLogger.log("Socket session", session.toString())

        //TODO
        //server.addListener(...)

        try {
            incoming.consume { /* Empty */ }
        } finally {
            //TODO
            //server.removeListener(...)
        }
    }
}

private suspend fun WebSocketServerSession.getUser(): User? {
    val userId = call.getUserId()
    return if (userId == null) {
        close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "User id is null"))
        null
    } else {
        User(userId)
    }
}

private suspend fun WebSocketServerSession.getSession(): NotesSession? {
    val session = call.sessions.get<NotesSession>()
    if (session == null) {
        //TODO check if this is called when client disconnects. If so, remove from socket list
        close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
    }
    return session
}
