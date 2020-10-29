package com.akjaw.fullerstack.authentication.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
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

class AuthenticationActivity : AppCompatActivity(R.layout.activity_authentication), DIAware {

    override val di: DI by DI.lazy {
        val customApplication = application as DIAware
        extend(customApplication.di)
        bind<FragmentActivity>() with singleton { this@AuthenticationActivity }
        import(activityScopedAuthenticationModule)
    }

    private lateinit var loadingIndicator: ProgressBar
    private lateinit var authenticationButton: Button
    private lateinit var viewFader: ViewFader

    private val userAuthenticator: UserAuthenticator by instance()
    private val afterAuthenticationLauncher: AfterAuthenticationLauncher by instance()
    private val tokenProvider: TokenProvider by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingIndicator = findViewById(R.id.loading_indicator)
        authenticationButton = findViewById(R.id.authentication_button)
        authenticationButton.setOnClickListener {
            onAuthenticationClicked()
        }

        val description = findViewById<View>(R.id.authentication_description)
        viewFader = ViewFader(
            views = listOf(description, authenticationButton)
        )
    }

    private fun onAuthenticationClicked() {
        lifecycleScope.launchWhenResumed {
            showLoading()
            val result = userAuthenticator.signInUser()
            val activity = this@AuthenticationActivity
            if (result == AuthenticationResult.SUCCESS) {
                tokenProvider.initializeToken()
                afterAuthenticationLauncher.launch(activity)
            } else {
                hideLoading()
                Toast.makeText(
                    activity,
                    getString(R.string.try_again_later),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun hideLoading() {
        loadingIndicator.visibility = View.GONE
        viewFader.fadeInViews(150)
    }

    private fun showLoading() {
        viewFader.fadeOutViews(150)
        loadingIndicator.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        viewFader.destroy()
    }
}
