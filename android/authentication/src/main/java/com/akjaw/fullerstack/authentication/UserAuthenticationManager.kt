package com.akjaw.fullerstack.authentication

import android.app.Activity

interface UserAuthenticationManager {

    fun isUserAuthenticated(): Boolean

    fun authenticateUser(activity: Activity)

}
