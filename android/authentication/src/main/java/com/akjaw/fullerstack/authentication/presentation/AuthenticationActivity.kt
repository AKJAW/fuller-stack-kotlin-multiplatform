package com.akjaw.fullerstack.authentication.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.akjaw.fullerstack.authentication.R
import com.auth0.android.Auth0
import com.auth0.android.authentication.storage.SecureCredentialsManager
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance

class AuthenticationActivity : AppCompatActivity(), DIAware {

    override val di: DI by DI.lazy {
        val customApplication = application as DIAware
        extend(customApplication.di)
    }

    private val auth0: Auth0 by instance()
    private val credentialsManager: SecureCredentialsManager by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        Log.d("Auth has creds", credentialsManager.hasValidCredentials().toString())
    }
}
