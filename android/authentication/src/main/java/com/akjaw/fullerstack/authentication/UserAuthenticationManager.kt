package com.akjaw.fullerstack.authentication

interface UserAuthenticationManager {

    fun isUserAuthenticated(): Boolean

    fun authenticateUser()

}
