package network

object ApiUrl {
    private const val PROTOCOL = "https://"
    const val BASE_URL = "fuller-stack-ktor.herokuapp.com"
    const val BASE_URL_WITH_PROTOCOL = "$PROTOCOL$BASE_URL"
    const val NOTES_URL_WITH_PROTOCOL = "$BASE_URL_WITH_PROTOCOL/notes"
    const val SOCKET_ENDPOINT = "notes/ws"
    const val SOCKET_URL_WITH_PROTOCOL = "$BASE_URL_WITH_PROTOCOL/notes/ws"
}
