package com.akjaw.fullerstack.authentication.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.authentication.R
import com.akjaw.fullerstack.authentication.UserAuthenticationManager
import com.akjaw.fullerstack.authentication.model.AuthenticationResult
import com.akjaw.fullerstack.authentication.navigation.AfterAuthenticationLauncher
import com.akjaw.fullerstack.authentication.token.TokenProvider
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class AuthenticationActivity : AppCompatActivity(), DIAware {

    override val di: DI by DI.lazy {
        val customApplication = application as DIAware
        extend(customApplication.di)
    }

    private lateinit var authenticationButton: Button

    private val userAuthenticationManager: UserAuthenticationManager by instance()
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
            val result = userAuthenticationManager.authenticateUser(activity)
            if (result == AuthenticationResult.SUCCESS) {
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
