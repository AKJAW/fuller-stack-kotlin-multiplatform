package com.akjaw.fullerstack.authentication.model

internal data class Auth0Config(
    val schema: String,
    val apiIdentifier: String,
    val scope: String
)
