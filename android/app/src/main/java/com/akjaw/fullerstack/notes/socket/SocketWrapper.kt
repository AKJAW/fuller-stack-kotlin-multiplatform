package com.akjaw.fullerstack.notes.socket

import com.akjaw.fullerstack.authentication.token.TokenProvider
import feature.socket.NoteSocket
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import network.NoteSchema
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class SocketWrapper(
    private val okHttpClient: OkHttpClient,
    private val tokenProvider: TokenProvider,
    private val socketRequest: Request
) : NoteSocket {

    private var socket: WebSocket? = null
    private var flow: Flow<List<NoteSchema>>? = null

    fun connect(): Flow<List<NoteSchema>> {
        return callbackFlow {
            val listener = SocketListener(
                onData = { json ->
                    val noteSchema = Json.decodeFromString(
                        ListSerializer(NoteSchema.serializer()),
                        json
                    )
                    sendBlocking(noteSchema)
                },
                onError = {
                    //TOOD find out what error is thrown, then decide
                    cancel()
                }
            )
            socket = okHttpClient.newWebSocket(socketRequest, listener)
            socket?.send("Bearer ${tokenProvider.getToken()?.jwt}")
            awaitClose { socket?.cancel() }
        }.apply {
            flow = this
        }

    }

    fun close() {
        flow = null
        socket?.close(1000, null)
    }

    override fun getNotesFlow(): Flow<List<NoteSchema>> {
        return flow ?: connect()
    }
}