package com.akjaw.fullerstack.authentication

import android.app.Activity
import android.content.Intent
import com.akjaw.fullerstack.authentication.presentation.AuthenticationActivity

internal class ActivityAuthenticationLauncher(private val activity: Activity) : AuthenticationLauncher {
    override fun showAuthenticationScreen() {
        val intent = Intent(activity, AuthenticationActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}
