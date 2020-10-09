package com.akjaw.fullerstack.notes.network

import com.akjaw.fullerstack.authentication.token.TokenProvider
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = tokenProvider.getToken()?.jwt ?: "ERROR"
        requestBuilder.addHeader("Authorization", "Bearer $token")

        return chain.proceed(requestBuilder.build())
    }
}
