package com.akjaw.fullerstack.authentication.composition

import android.content.Context
import com.akjaw.fullerstack.authentication.ActivityAuthenticationLauncher
import com.akjaw.fullerstack.authentication.Auth0UserAuthenticator
import com.akjaw.fullerstack.authentication.AuthenticationLauncher
import com.akjaw.fullerstack.authentication.GetUserProfile
import com.akjaw.fullerstack.authentication.R
import com.akjaw.fullerstack.authentication.UserAuthenticator
import com.akjaw.fullerstack.authentication.model.Auth0Config
import com.akjaw.fullerstack.authentication.token.TokenProvider
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val authenticationModule = DI.Module("authenticationModule") {
    import(auth0Module)
    bind() from singleton {
        val context = instance<Context>("ApplicationContext")
        Auth0Config(
            schema = context.getString(R.string.com_auth0_schema),
            apiIdentifier = context.getString(R.string.api_identifier),
            scope = "openid profile email offline_access"
        )
    }
    bind<UserAuthenticator>() with singleton {
        Auth0UserAuthenticator(
            context = instance("ApplicationContext"),
            auth0 = instance(),
            auth0Config = instance(),
            credentialsManager = instance()
        )
    }
    bind() from singleton { GetUserProfile(instance(), instance()) }
    bind<AuthenticationLauncher>() with singleton { ActivityAuthenticationLauncher() }
    bind() from singleton { TokenProvider(instance()) }
}
