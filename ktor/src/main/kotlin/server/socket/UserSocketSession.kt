package server.socket

import server.jwt.TokenParser
import server.storage.model.User

class UserSocketSession(private val tokenParser: TokenParser) {

    private var userId: String? = null

    fun initialize(jwtToken: String?) {
        userId = jwtToken?.let { tokenParser.getUserId(it) }
    }

    fun getUser(): User? {
        val userId = userId ?: return null
        return User(userId)
    }
}
