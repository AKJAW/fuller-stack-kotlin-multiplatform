package network

expect suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResponse<T>
