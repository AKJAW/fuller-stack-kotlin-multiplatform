package com.akjaw.fullerstack.authentication

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.akjaw.fullerstack.authentication.model.AuthenticationResult
import com.akjaw.fullerstack.authentication.model.UserProfile
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.callback.BaseCallback
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.auth0.android.result.UserProfile as Auth0UserProfile

internal class Auth0UserAuthenticationManager(
    private val auth0: Auth0,
    private val credentialsManager: SecureCredentialsManager,
    private val authenticationAPIClient: AuthenticationAPIClient
) : UserAuthenticationManager {
    private var credentials: Credentials? = null
    private var userProfile: UserProfile? = null

    override fun isUserAuthenticated(): Boolean {
        return credentialsManager.hasValidCredentials()
    }

    override suspend fun authenticateUser(activity: Activity): AuthenticationResult {
        val schema: String = activity.getString(R.string.com_auth0_schema)
        val apiIdentifier: String = activity.getString(R.string.api_identifier)
        return suspendCoroutine { continuation ->
            WebAuthProvider
                .login(auth0)
                .withScheme(schema)
                .withAudience(apiIdentifier)
                .withScope("openid profile email offline_access")
                .start(
                    activity,
                    createAuthCallback(continuation)
                )
        }
    }

    override suspend fun getUserProfile(): UserProfile? = suspendCoroutine { continuation ->
        if (userProfile != null) {
            continuation.resume(userProfile)
            return@suspendCoroutine
        }
        val accessToken = credentials?.accessToken
        if (accessToken == null) {
            continuation.resume(null)
            return@suspendCoroutine
        }
        authenticationAPIClient.userInfo(accessToken)
            .start(object : BaseCallback<Auth0UserProfile, AuthenticationException> {

                override fun onSuccess(payload: Auth0UserProfile?) {
                    //TODO check these values
                    val name = payload?.nickname.orEmpty()
                    val email = payload?.email.orEmpty()
                    userProfile = UserProfile(name = name, email = email)
                    continuation.resume(userProfile)
                }

                override fun onFailure(error: AuthenticationException?) {
                    Log.d("Auth", "userInfo failure $error")
                    continuation.resume(null)
                }

            })
    }

    private fun createAuthCallback(continuation: Continuation<AuthenticationResult>) =
        object : AuthCallback {
            override fun onSuccess(credentials: Credentials) {
                Log.d("Auth", "authCallback succes $credentials")
                this@Auth0UserAuthenticationManager.credentials = credentials
                credentialsManager.saveCredentials(credentials)
                continuation.resume(AuthenticationResult.SUCCESS)
            }

            override fun onFailure(dialog: Dialog) {
                Log.d("Auth", "authCallback failure dialog")
                //TODO will this cause a main thread crash
                dialog.show()
                continuation.resume(AuthenticationResult.FAILURE)
            }

            override fun onFailure(exception: AuthenticationException?) {
                Log.d("Auth", "authCallback failure $exception")
                continuation.resume(AuthenticationResult.FAILURE)
            }
        }
}
