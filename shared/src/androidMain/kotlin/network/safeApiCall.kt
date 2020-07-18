package network

import retrofit2.HttpException
import java.io.IOException

@Suppress("TooGenericExceptionCaught")
actual suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResponse<T> {
    return try {
        val result = block()
        NetworkResponse.Success(result)
    } catch (throwable: Throwable) {
        throwable.printStackTrace()
        when(throwable) {
            is IOException -> NetworkResponse.NetworkError
            is HttpException -> NetworkResponse.ApiError
            else -> NetworkResponse.UnknownError
        }
    }
}
