package com.akjaw.fullerstack.authentication

internal class Auth0UserAuthenticationManager : UserAuthenticationManager {

    override fun isUserAuthenticated(): Boolean {
        return false
    }

    override fun authenticateUser() {

    }
}
