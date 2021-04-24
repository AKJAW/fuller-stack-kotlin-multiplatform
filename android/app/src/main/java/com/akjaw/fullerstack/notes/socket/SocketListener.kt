package com.akjaw.fullerstack.notes.socket

import com.akjaw.fullerstack.helpers.logger.log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class SocketListener(
    private val onData: (String) -> Unit,
    private val onError: (Throwable) -> Unit
) : WebSocketListener() {

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        text.log("Socket string")
        onData(text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        bytes.log("Socket bytes")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        t.log("Socket ERR")
        response.log("Socket ERR")
        onError(t)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        code.log("Socket closed")
        reason.log("Socket closed")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        code.log("Socket closing")
        reason.log("Socket closing")
    }
}
