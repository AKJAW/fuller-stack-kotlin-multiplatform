package network

actual suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResponse<T> {
    return try {
        val result = block()
        NetworkResponse.Success(result)
    } catch (throwable: Throwable) {
        console.log(throwable.message)
        console.log(throwable.cause)
        when (throwable) { // TODO what errors does Ktor throw
            else -> NetworkResponse.UnknownError
        }
    }
}
