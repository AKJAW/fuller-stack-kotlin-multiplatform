package server.routes.notes

import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.Route
import io.ktor.websocket.WebSocketServerSession
import io.ktor.websocket.webSocket
import kotlinx.coroutines.channels.consumeEach
import org.kodein.di.instance
import org.kodein.di.ktor.di
import server.jwt.TokenParser
import server.logger.ApiLogger
import server.routes.getSessionId
import server.routes.getUserId
import server.socket.SocketServer
import server.socket.UserSocketSession
import server.storage.model.User


fun Route.notesSocket() {
    val apiLogger: ApiLogger by di().instance()
    val socketServer: SocketServer by di().instance()
    val tokenParser: TokenParser by di().instance()

    webSocket("/notes/ws") {
        val userSocketSession = UserSocketSession(tokenParser)
        apiLogger.log("Socket userSocketSession", userSocketSession.toString())
        val sessionId = call.getSessionId()
        if (sessionId == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }
        apiLogger.log("Socket sessionId", sessionId)

        try {
            incoming.consumeEach { frame ->
                apiLogger.log("Socket incoming frame", frame.toString())
                val token = frame.getBearerToken()
                userSocketSession.initialize(token)
                userSocketSession.getUser()?.let { user ->
                    apiLogger.log("Socket incoming user", user.toString())
                    socketServer.addListener(sessionId, user, this)
                }
            }
        } finally {
            userSocketSession.getUser()?.let { user ->
                apiLogger.log("Socket ended", user.toString())
                socketServer.removeListener(sessionId, user, this)
            }
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

private fun Frame.getBearerToken(): String? {
    if (this is Frame.Text) {
        val text = readText()
        if(text.startsWith("Bearer")) {
            return text.split(" ").last()
        }
    }
    return null
}
