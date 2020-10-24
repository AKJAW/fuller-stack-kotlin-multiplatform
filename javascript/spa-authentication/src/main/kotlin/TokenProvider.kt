class TokenProvider {
    var accessToken: String? = null
        private set

    fun initializeToken(token: String) {
        accessToken = token
    }

    fun revokeToken() {
        accessToken = null
    }
}
