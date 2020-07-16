package network

actual suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResponse<T>{
    TODO("Not yet implemented")
}
