package com.akjaw.fullerstack.helpers.network

sealed class NetworkResponse {
    class Success <T> (val result: T): NetworkResponse()
    object ApiError: NetworkResponse()
    object NetworkError: NetworkResponse()
    object UnknownError: NetworkResponse()
}
