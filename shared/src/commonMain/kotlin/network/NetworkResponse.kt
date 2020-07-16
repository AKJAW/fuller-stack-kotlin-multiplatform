package network

sealed class NetworkResponse <out T> {
    class Success <out T> (val result: T): NetworkResponse<T>()
    object ApiError: NetworkResponse<Nothing>()
    object NetworkError: NetworkResponse<Nothing>()
    object UnknownError: NetworkResponse<Nothing>()
}
