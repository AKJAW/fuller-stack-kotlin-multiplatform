package com.akjaw.fullerstack.authentication

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.akjaw.fullerstack.authentication.model.Auth0Config
import com.akjaw.fullerstack.authentication.model.AuthenticationResult
import com.auth0.android.Auth0
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class Auth0UserAuthenticator(
    private val activity: Activity,
    private val auth0: Auth0,
    private val auth0Config: Auth0Config,
    private val credentialsManager: SecureCredentialsManager
) : UserAuthenticator {

    override fun isUserAuthenticated(): Boolean {
        return credentialsManager.hasValidCredentials()
    }

    override suspend fun signInUser(): AuthenticationResult {
        return suspendCoroutine { continuation ->
            WebAuthProvider
                .login(auth0)
                .withScheme(auth0Config.schema)
                .withAudience(auth0Config.apiIdentifier)
                .withScope(auth0Config.scope)
                .start(
                    activity,
                    createLogInCallback(continuation)
                )
        }
    }

    private fun createLogInCallback(continuation: Continuation<AuthenticationResult>) =
        object : AuthCallback {
            override fun onSuccess(credentials: Credentials) {
                Log.d("Auth", "authCallback success $credentials")
                credentialsManager.saveCredentials(credentials)
                continuation.resume(AuthenticationResult.SUCCESS)
            }

            override fun onFailure(dialog: Dialog) {
                Log.d("Auth", "authCallback failure dialog")
                //TODO will this cause a main thread crash?
                dialog.show()
                continuation.resume(AuthenticationResult.FAILURE)
            }

            override fun onFailure(exception: AuthenticationException?) {
                Log.d("Auth", "authCallback failure $exception")
                continuation.resume(AuthenticationResult.FAILURE)
            }
        }

    override suspend fun signOutUser(): AuthenticationResult {
        return suspendCoroutine { continuation ->
            WebAuthProvider
                .logout(auth0)
                .withScheme(auth0Config.schema)
                .start(activity, createLogOutCallback(continuation))
        }
    }

    private fun createLogOutCallback(continuation: Continuation<AuthenticationResult>) =
        object : VoidCallback {
            override fun onSuccess(payload: Void?) {
                credentialsManager.clearCredentials()
                return continuation.resume(AuthenticationResult.SUCCESS)
            }

            override fun onFailure(error: Auth0Exception?) {
                Log.d("Auth", "authCallback failure $error")
                return continuation.resume(AuthenticationResult.FAILURE)
            }
        }
}
