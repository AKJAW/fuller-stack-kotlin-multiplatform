package com.akjaw.fullerstack.authentication.token

import android.util.Log
import com.akjaw.fullerstack.authentication.model.AccessToken
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.callback.BaseCallback
import com.auth0.android.result.Credentials
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TokenProvider(
    private val credentialsManager: SecureCredentialsManager
) {

    enum class Result {
        SUCCESS,
        FAILURE
    }

    private var accessToken: AccessToken? = null

    fun getToken(): AccessToken? = accessToken

    suspend fun initializeToken(): Result = suspendCoroutine { continuation ->
        credentialsManager.getCredentials(object :BaseCallback<Credentials, CredentialsManagerException> {

            override fun onSuccess(payload: Credentials?) {
                val jwt = payload?.accessToken
                if (jwt == null) {
                    continuation.resume(Result.FAILURE)
                } else {
                    accessToken = AccessToken(jwt)
                    continuation.resume(Result.SUCCESS)
                }
            }

            override fun onFailure(error: CredentialsManagerException?) {
                Log.d("Auth", "getCredentials failure $error")
                continuation.resume(Result.FAILURE)
            }
        })
    }
}
