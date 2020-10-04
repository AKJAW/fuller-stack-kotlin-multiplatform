package com.akjaw.fullerstack.authentication

import android.app.Activity
import android.app.Dialog
import android.util.Log
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials

internal class Auth0UserAuthenticationManager(
    private val auth0: Auth0,
    private val credentialsManager: SecureCredentialsManager
) : UserAuthenticationManager {

    override fun isUserAuthenticated(): Boolean {
        return credentialsManager.hasValidCredentials()
    }

    override fun authenticateUser(activity: Activity) {
        val schema: String = activity.getString(R.string.com_auth0_schema)
        val apiIdentifier: String = activity.getString(R.string.api_identifier)
        return WebAuthProvider
            .login(auth0)
            .withScheme(schema)
            .withAudience(apiIdentifier)
            .withScope("openid profile email offline_access")
            .start(
                activity,
                authCallback
            )
    }

    private val authCallback = object : AuthCallback {
        override fun onSuccess(credentials: Credentials) {
            Log.d("Auth", "succes $credentials")
        }

        override fun onFailure(dialog: Dialog) {
            Log.d("Auth", "failure dialog")
        }

        override fun onFailure(exception: AuthenticationException?) {
            Log.d("Auth", "failure $exception")
        }
    }
}
