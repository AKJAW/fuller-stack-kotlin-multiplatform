package com.akjaw.fullerstack.authentication

import android.app.Activity
import com.akjaw.fullerstack.authentication.model.AuthenticationResult
import com.akjaw.fullerstack.authentication.model.UserProfile

//TODO should this be internal?
interface UserAuthenticationManager {

    fun isUserAuthenticated(): Boolean

    suspend fun authenticateUser(activity: Activity): AuthenticationResult

    suspend fun getUserProfile(): UserProfile?

}
