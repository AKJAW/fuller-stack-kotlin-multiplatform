package server.socket

import io.ktor.http.cio.websocket.WebSocketSession

data class SocketHolder(val sessionId: String, val socketSession: WebSocketSession)
