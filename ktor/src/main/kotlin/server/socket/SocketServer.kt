package server.socket

import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import network.NoteSchema
import server.logger.ApiLogger
import server.storage.NotesStorage
import server.storage.model.User
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class SocketServer(
    private val apiLogger: ApiLogger,
    private val notesStorage: NotesStorage
) {

    private val listeners = ConcurrentHashMap<User, MutableList<SocketHolder>>()

    fun addListener(sessionId: String, user: User, webSocketSession: WebSocketSession) {
        val sockets = listeners.computeIfAbsent(user) { CopyOnWriteArrayList() }
        val holder = SocketHolder(sessionId, webSocketSession)

        if (sockets.contains(holder).not()) {
            sockets.add(holder)
            apiLogger.log("Socket Server", "add $holder for $user")
        }
    }

    fun removeListener(sessionId: String, user: User, webSocketSession: WebSocketSession) {
        val connections = listeners[user]
        val holder = SocketHolder(sessionId, webSocketSession)
        apiLogger.log("Socket Server", "remove $holder from $user")
        connections?.remove(holder)
    }

    suspend fun sendUpdateToUser(user: User, sessionId: String?) {
        apiLogger.log("Socket Server", "listeners $listeners")
        val notes = notesStorage.getNotes(user)
        listeners[user]?.forEach { holder ->
            if (holder.sessionId != sessionId) {
                apiLogger.log("Socket Server", "user $user, sessionId ${holder.sessionId}, notes $notes")
                try {
                    val json = Json.encodeToString(ListSerializer(NoteSchema.serializer()), notes)
                    holder.socketSession.send(Frame.Text(json))
                } catch (e: Throwable) {
                    apiLogger.log("Socket Server", "sendUpdateToUser error $e")
                }
            }
        }
    }
}
