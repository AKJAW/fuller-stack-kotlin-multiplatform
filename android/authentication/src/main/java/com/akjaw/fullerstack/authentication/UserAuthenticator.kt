package com.akjaw.fullerstack.authentication

import android.app.Activity
import com.akjaw.fullerstack.authentication.model.AuthenticationResult

interface UserAuthenticator {

    fun isUserAuthenticated(): Boolean

    suspend fun signInUser(activity: Activity): AuthenticationResult

    suspend fun signOutUser(activity: Activity): AuthenticationResult
}
