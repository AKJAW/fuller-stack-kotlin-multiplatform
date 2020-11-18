package com.akjaw.fullerstack.authentication.composition

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

internal val auth0Module = DI.Module("auth0Module") {
    bind() from singleton {
        Auth0(instance("ApplicationContext")).apply {
            isOIDCConformant = true
        }
    }
    bind() from singleton { AuthenticationAPIClient(instance<Auth0>()) }
    bind() from singleton { SharedPreferencesStorage(instance("ApplicationContext")) }
    bind() from singleton {
        SecureCredentialsManager(
            instance<Context>("ApplicationContext"),
            instance<AuthenticationAPIClient>(),
            instance<SharedPreferencesStorage>()
        )
    }
}
