package network

import io.ktor.client.features.ServerResponseException

actual suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResponse<T> {
    return try {
        val result = block()
        NetworkResponse.Success(result)
    } catch (throwable: Throwable) {
        when (throwable) {
            is ServerResponseException -> NetworkResponse.ApiError
            else -> NetworkResponse.UnknownError
        }
    }
}
