package com.akjaw.fullerstack.authentication

import android.app.Activity
import android.content.Intent
import com.akjaw.fullerstack.authentication.presentation.AuthenticationActivity

internal class ActivityAuthenticationLauncher : AuthenticationLauncher {
    override fun showAuthenticationScreen(activity: Activity) {
        val intent = Intent(activity, AuthenticationActivity::class.java)
        activity.startActivity(intent)
    }
}
