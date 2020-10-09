package com.akjaw.fullerstack.screens.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.authentication.AuthenticationLauncher
import com.akjaw.fullerstack.authentication.UserAuthenticationManager
import com.akjaw.fullerstack.authentication.navigation.AfterAuthenticationLauncher
import com.akjaw.fullerstack.authentication.token.TokenProvider
import com.akjaw.fullerstack.screens.common.FullerStackApp
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

//TODO base activity?
class SplashActivity : AppCompatActivity(), DIAware {
    override val di: DI by DI.lazy {
        val customApplication = application as FullerStackApp
        extend(customApplication.di)
    }

    private val userAuthenticationManager: UserAuthenticationManager by instance()
    private val authenticationLauncher: AuthenticationLauncher by instance()
    private val tokenProvider: TokenProvider by instance()
    private val afterAuthenticationLauncher: AfterAuthenticationLauncher by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_Splash)
        super.onCreate(savedInstanceState)

        if (userAuthenticationManager.isUserAuthenticated()) {
            lifecycleScope.launchWhenResumed {
                tokenProvider.initializeToken()
                afterAuthenticationLauncher.launch(this@SplashActivity)
                finish()
            }
        } else {
            authenticationLauncher.showAuthenticationScreen(this)
            finish()
        }
    }
}
