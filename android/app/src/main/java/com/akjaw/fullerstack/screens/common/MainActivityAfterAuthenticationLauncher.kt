package com.akjaw.fullerstack.screens.common

import android.app.Activity
import android.content.Intent
import com.akjaw.fullerstack.authentication.navigation.AfterAuthenticationLauncher
import com.akjaw.fullerstack.screens.common.main.MainActivity

class MainActivityAfterAuthenticationLauncher: AfterAuthenticationLauncher {

    override fun launch(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}
