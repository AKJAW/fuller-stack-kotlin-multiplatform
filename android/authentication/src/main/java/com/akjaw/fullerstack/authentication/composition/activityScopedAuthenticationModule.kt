package com.akjaw.fullerstack.authentication.composition

import com.akjaw.fullerstack.authentication.ActivityAuthenticationLauncher
import com.akjaw.fullerstack.authentication.Auth0UserAuthenticator
import com.akjaw.fullerstack.authentication.AuthenticationLauncher
import com.akjaw.fullerstack.authentication.UserAuthenticator
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val activityScopedAuthenticationModule = DI.Module("activityScopedAuthenticationModule") {
    bind<AuthenticationLauncher>() with singleton { ActivityAuthenticationLauncher(instance()) }
    bind<UserAuthenticator>() with singleton {
        Auth0UserAuthenticator(
            activity = instance(),
            auth0 = instance(),
            auth0Config = instance(),
            credentialsManager = instance()
        )
    }
}
