package com.akjaw.fullerstack.authentication.navigation

import android.app.Activity

interface AfterAuthenticationLauncher {

    fun launch(activity: Activity)
}
