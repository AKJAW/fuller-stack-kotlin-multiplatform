package socket

import TokenProvider
import feature.socket.NoteSocket
import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.http.HttpMethod
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import network.NoteSchema

class KtorNoteSocket(
    private val client: HttpClient,
    private val tokenProvider: TokenProvider
) : NoteSocket {

    private var session: WebSocketSession? = null
    private var flow: Flow<List<NoteSchema>>? = null

    override fun close() {
        flow = null
        session?.cancel()
        session = null
    }

    override fun getNotesFlow(): Flow<List<NoteSchema>> {
        return flow ?: connect()
    }

    private fun connect(): Flow<List<NoteSchema>> = callbackFlow {
        session = client.webSocketSession(
            method = HttpMethod.Get,
            host = "fuller-stack-ktor.herokuapp.com",
            port = 9001,
            path = "/notes/ws",
        )

        session?.send(Frame.Text("Bearer ${tokenProvider.accessToken}"))

        try {
            listenToSocket()
        } catch (e: Exception) {
            cancel()
        }

        awaitClose { session?.cancel() }
    }.apply {
        flow = this
    }

    private suspend fun ProducerScope<List<NoteSchema>>.listenToSocket() {
        while (true) {
            val frame = session?.incoming?.receive()
            if (frame is Frame.Text) {
                val json = frame.readText()
                val noteSchema = Json.decodeFromString(
                    ListSerializer(NoteSchema.serializer()),
                    json
                )
                send(noteSchema)
            }
        }
    }
}
