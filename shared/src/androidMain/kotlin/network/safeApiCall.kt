package network

import retrofit2.HttpException
import java.io.IOException

actual suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResponse<T> {
    return try {
        val result = block()
        NetworkResponse.Success(result)
    } catch (throwable: Throwable) {
        when(throwable) {
            is IOException -> NetworkResponse.NetworkError
            is HttpException -> NetworkResponse.ApiError
            else -> NetworkResponse.UnknownError
        }
    }
}
