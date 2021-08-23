@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "MatchingDeclarationName"
)

@Suppress("MatchingDeclarationName")
external interface AppState {
    var returnTo: String?
        get() = definedExternally
        set(value) = definedExternally
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Any)
}

// external interface `T$0` {
//    var defaultScope: String?
//        get() = definedExternally
//        set(value) = definedExternally
// }
//
// external interface Auth0ProviderOptions {
//    var children: Any?
//        get() = definedExternally
//        set(value) = definedExternally
//    var onRedirectCallback: ((appState: AppState) -> Unit)?
//        get() = definedExternally
//        set(value) = definedExternally
//    var domain: String
//    var issuer: String?
//        get() = definedExternally
//        set(value) = definedExternally
//    var clientId: String
//    var redirectUri: String?
//        get() = definedExternally
//        set(value) = definedExternally
//    var leeway: Number?
//        get() = definedExternally
//        set(value) = definedExternally
//    var cacheLocation: String? /* 'memory' | 'localstorage' */
//        get() = definedExternally
//        set(value) = definedExternally
//    var useRefreshTokens: Boolean?
//        get() = definedExternally
//        set(value) = definedExternally
//    var authorizeTimeoutInSeconds: Number?
//        get() = definedExternally
//        set(value) = definedExternally
//    var advancedOptions: `T$0`?
//        get() = definedExternally
//        set(value) = definedExternally
//    var maxAge: dynamic /* String? | Number? */
//        get() = definedExternally
//        set(value) = definedExternally
//    var scope: String?
//        get() = definedExternally
//        set(value) = definedExternally
//    var audience: String?
//        get() = definedExternally
//        set(value) = definedExternally
//    @nativeGetter
//    operator fun get(key: String): Any?
//    @nativeSetter
//    operator fun set(key: String, value: Any)
// }
