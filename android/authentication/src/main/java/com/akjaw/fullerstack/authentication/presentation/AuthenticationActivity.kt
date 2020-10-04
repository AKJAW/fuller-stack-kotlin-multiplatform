package com.akjaw.fullerstack.authentication.presentation

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.akjaw.fullerstack.authentication.R
import com.akjaw.fullerstack.authentication.UserAuthenticationManager
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        authenticationButton = findViewById(R.id.authentication_button)
        authenticationButton.setOnClickListener {
            userAuthenticationManager.authenticateUser(this)
        }
    }
}
