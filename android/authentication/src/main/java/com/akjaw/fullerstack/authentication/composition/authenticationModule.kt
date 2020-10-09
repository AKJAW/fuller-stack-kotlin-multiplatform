package com.akjaw.fullerstack.authentication.composition

import com.akjaw.fullerstack.authentication.ActivityAuthenticationLauncher
import com.akjaw.fullerstack.authentication.Auth0UserAuthenticationManager
import com.akjaw.fullerstack.authentication.AuthenticationLauncher
import com.akjaw.fullerstack.authentication.UserAuthenticationManager
import com.akjaw.fullerstack.authentication.token.TokenProvider
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val authenticationModule = DI.Module("authenticationModule") {
    bind<UserAuthenticationManager>() with singleton {
        Auth0UserAuthenticationManager(
            auth0 = instance(),
            credentialsManager = instance(),
            authenticationAPIClient = instance()
        )
    }
    bind<AuthenticationLauncher>() with singleton { ActivityAuthenticationLauncher() }
    bind() from singleton { TokenProvider(instance()) }
    import(auth0Module)
}
