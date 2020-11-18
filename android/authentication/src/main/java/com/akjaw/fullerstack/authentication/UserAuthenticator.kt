package com.akjaw.fullerstack.authentication

import com.akjaw.fullerstack.authentication.model.AuthenticationResult

interface UserAuthenticator {

    fun isUserAuthenticated(): Boolean

    suspend fun signInUser(): AuthenticationResult

    suspend fun signOutUser(): AuthenticationResult
}
