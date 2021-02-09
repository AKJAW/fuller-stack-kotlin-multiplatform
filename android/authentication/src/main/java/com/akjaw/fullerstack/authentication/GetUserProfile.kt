package com.akjaw.fullerstack.authentication

import android.util.Log
import com.akjaw.fullerstack.authentication.model.UserProfile
import com.akjaw.fullerstack.authentication.token.TokenProvider
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.BaseCallback
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.auth0.android.result.UserProfile as Auth0UserProfile

class GetUserProfile(
    private val authenticationAPIClient: AuthenticationAPIClient,
    private val tokenProvider: TokenProvider
) {

    suspend operator fun invoke(): UserProfile? = suspendCoroutine { continuation ->
        val jwtToken = tokenProvider.getToken()?.jwt
        if (jwtToken == null) {
            continuation.resume(null)
            return@suspendCoroutine
        }
        authenticationAPIClient.userInfo(jwtToken)
            .start(object : BaseCallback<Auth0UserProfile, AuthenticationException> {

                override fun onSuccess(payload: Auth0UserProfile?) {
                    //TODO check these values
                    val name = payload?.nickname.orEmpty()
                    val email = payload?.email.orEmpty()
                    val profilePictureUrl = payload?.pictureURL.orEmpty()
                    val userProfile = UserProfile(name = name, email = email, profilePictureUrl = profilePictureUrl)
                    continuation.resume(userProfile)
                }

                override fun onFailure(error: AuthenticationException) {
                    Log.d("Auth", "userInfo failure $error")
                    continuation.resume(null)
                }

            })
    }
}
