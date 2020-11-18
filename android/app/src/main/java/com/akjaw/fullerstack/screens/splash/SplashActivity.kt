package com.akjaw.fullerstack.screens.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.authentication.AuthenticationLauncher
import com.akjaw.fullerstack.authentication.UserAuthenticator
import com.akjaw.fullerstack.authentication.navigation.AfterAuthenticationLauncher
import com.akjaw.fullerstack.authentication.token.TokenProvider
import com.akjaw.fullerstack.screens.common.base.BaseActivity
import org.kodein.di.instance

class SplashActivity : BaseActivity() {

    private val userAuthenticator: UserAuthenticator by instance()
    private val authenticationLauncher: AuthenticationLauncher by instance()
    private val tokenProvider: TokenProvider by instance()
    private val afterAuthenticationLauncher: AfterAuthenticationLauncher by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Splash)
        super.onCreate(savedInstanceState)

        if (userAuthenticator.isUserAuthenticated()) {
            lifecycleScope.launchWhenResumed {
                tokenProvider.initializeToken()
                afterAuthenticationLauncher.launch(this@SplashActivity)
                finish()
            }
        } else {
            authenticationLauncher.showAuthenticationScreen()
        }
    }
}
