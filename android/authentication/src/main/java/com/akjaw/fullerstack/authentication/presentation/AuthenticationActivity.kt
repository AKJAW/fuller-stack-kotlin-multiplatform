package com.akjaw.fullerstack.authentication.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.authentication.R
import com.akjaw.fullerstack.authentication.UserAuthenticator
import com.akjaw.fullerstack.authentication.composition.activityScopedAuthenticationModule
import com.akjaw.fullerstack.authentication.model.AuthenticationResult
import com.akjaw.fullerstack.authentication.navigation.AfterAuthenticationLauncher
import com.akjaw.fullerstack.authentication.token.TokenProvider
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

class AuthenticationActivity : AppCompatActivity(), DIAware {

    override val di: DI by DI.lazy {
        val customApplication = application as DIAware
        extend(customApplication.di)
        bind<FragmentActivity>() with singleton { this@AuthenticationActivity }
        import(activityScopedAuthenticationModule)
    }

    private lateinit var authenticationButton: Button

    private val userAuthenticator: UserAuthenticator by instance()
    private val afterAuthenticationLauncher: AfterAuthenticationLauncher by instance()
    private val tokenProvider: TokenProvider by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        authenticationButton = findViewById(R.id.authentication_button)
        authenticationButton.setOnClickListener {
            onAuthenticationClicked()
        }
    }

    private fun onAuthenticationClicked() {
        lifecycleScope.launchWhenResumed {
            val activity = this@AuthenticationActivity
            val result = userAuthenticator.signInUser()
            if (result == AuthenticationResult.SUCCESS) {
                //TODO hide Button, show loading
                tokenProvider.initializeToken()
                afterAuthenticationLauncher.launch(activity)
            } else {
                Toast.makeText(
                    activity,
                    getString(R.string.try_again_later),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
