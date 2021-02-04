package server.socket

import io.ktor.application.ApplicationCall
import kotlinx.coroutines.launch
import server.logger.ApiLogger
import server.routes.getSessionId
import server.storage.model.User

class SocketNotifier(
    private val apiLogger: ApiLogger,
    private val socketServer: SocketServer
) {

    fun notifySocketOfChange(call: ApplicationCall, user: User) {
        call.application.launch {
            val sessionId = call.getSessionId()
            apiLogger.log("Notify socket", "sessionId $sessionId")
            socketServer.sendUpdateToUser(user, sessionId)
        }
    }
}
