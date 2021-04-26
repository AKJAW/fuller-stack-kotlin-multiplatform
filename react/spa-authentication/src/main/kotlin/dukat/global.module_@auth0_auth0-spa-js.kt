@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS"
)

@Suppress("VariableNaming")
external interface BaseLoginOptions {
    var display: String? /* 'page' | 'popup' | 'touch' | 'wap' */
        get() = definedExternally
        set(value) = definedExternally
    var prompt: String? /* 'none' | 'login' | 'consent' | 'select_account' */
        get() = definedExternally
        set(value) = definedExternally
    var max_age: dynamic /* String? | Number? */
        get() = definedExternally
        set(value) = definedExternally
    var ui_locales: String?
        get() = definedExternally
        set(value) = definedExternally
    var id_token_hint: String?
        get() = definedExternally
        set(value) = definedExternally
    var login_hint: String?
        get() = definedExternally
        set(value) = definedExternally
    var acr_values: String?
        get() = definedExternally
        set(value) = definedExternally
    var scope: String?
        get() = definedExternally
        set(value) = definedExternally
    var audience: String?
        get() = definedExternally
        set(value) = definedExternally
    var connection: String?
        get() = definedExternally
        set(value) = definedExternally
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Any)
}

external interface AdvancedOptions {
    var defaultScope: String?
        get() = definedExternally
        set(value) = definedExternally
}

@Suppress("ClassNaming")
external interface `T$1` {
    var name: String
    var version: String
}

@Suppress("VariableNaming")
external interface Auth0ClientOptions : BaseLoginOptions {
    var domain: String
    var issuer: String?
        get() = definedExternally
        set(value) = definedExternally
    var client_id: String
    var redirect_uri: String?
        get() = definedExternally
        set(value) = definedExternally
    var leeway: Number?
        get() = definedExternally
        set(value) = definedExternally
    var cacheLocation: String? /* 'memory' | 'localstorage' */
        get() = definedExternally
        set(value) = definedExternally
    var useRefreshTokens: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var authorizeTimeoutInSeconds: Number?
        get() = definedExternally
        set(value) = definedExternally
    var auth0Client: `T$1`?
        get() = definedExternally
        set(value) = definedExternally
    var legacySameSiteCookie: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var advancedOptions: AdvancedOptions?
        get() = definedExternally
        set(value) = definedExternally
}

@Suppress("VariableNaming")
external interface RedirectLoginOptions : BaseLoginOptions {
    var redirect_uri: String?
        get() = definedExternally
        set(value) = definedExternally
    var appState: Any?
        get() = definedExternally
        set(value) = definedExternally
    var fragment: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RedirectLoginResult {
    var appState: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PopupLoginOptions : BaseLoginOptions

external interface PopupConfigOptions {
    var timeoutInSeconds: Number?
        get() = definedExternally
        set(value) = definedExternally
    var popup: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetUserOptions {
    var scope: String?
        get() = definedExternally
        set(value) = definedExternally
    var audience: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface GetIdTokenClaimsOptions {
    var scope: String?
        get() = definedExternally
        set(value) = definedExternally
    var audience: String?
        get() = definedExternally
        set(value) = definedExternally
}

@Suppress("VariableNaming")
external interface GetTokenSilentlyOptions {
    var ignoreCache: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var redirect_uri: String?
        get() = definedExternally
        set(value) = definedExternally
    var scope: String?
        get() = definedExternally
        set(value) = definedExternally
    var audience: String?
        get() = definedExternally
        set(value) = definedExternally
    var timeoutInSeconds: Number?
        get() = definedExternally
        set(value) = definedExternally
}

val EMPTY_GET_TOKEN_SILENTLY_OPTIONS = object : GetTokenSilentlyOptions { }

external interface GetTokenWithPopupOptions : PopupLoginOptions

@Suppress("VariableNaming")
external interface LogoutOptions {
    var returnTo: String?
        get() = definedExternally
        set(value) = definedExternally
    var client_id: String?
        get() = definedExternally
        set(value) = definedExternally
    var federated: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var localOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

@Suppress("VariableNaming")
external interface IdToken {
    var __raw: String
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var given_name: String?
        get() = definedExternally
        set(value) = definedExternally
    var family_name: String?
        get() = definedExternally
        set(value) = definedExternally
    var middle_name: String?
        get() = definedExternally
        set(value) = definedExternally
    var nickname: String?
        get() = definedExternally
        set(value) = definedExternally
    var preferred_username: String?
        get() = definedExternally
        set(value) = definedExternally
    var profile: String?
        get() = definedExternally
        set(value) = definedExternally
    var picture: String?
        get() = definedExternally
        set(value) = definedExternally
    var website: String?
        get() = definedExternally
        set(value) = definedExternally
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
    var email_verified: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var gender: String?
        get() = definedExternally
        set(value) = definedExternally
    var birthdate: String?
        get() = definedExternally
        set(value) = definedExternally
    var zoneinfo: String?
        get() = definedExternally
        set(value) = definedExternally
    var locale: String?
        get() = definedExternally
        set(value) = definedExternally
    var phone_number: String?
        get() = definedExternally
        set(value) = definedExternally
    var phone_number_verified: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var address: String?
        get() = definedExternally
        set(value) = definedExternally
    var updated_at: String?
        get() = definedExternally
        set(value) = definedExternally
    var iss: String?
        get() = definedExternally
        set(value) = definedExternally
    var aud: String?
        get() = definedExternally
        set(value) = definedExternally
    var exp: Number?
        get() = definedExternally
        set(value) = definedExternally
    var nbf: Number?
        get() = definedExternally
        set(value) = definedExternally
    var iat: Number?
        get() = definedExternally
        set(value) = definedExternally
    var jti: String?
        get() = definedExternally
        set(value) = definedExternally
    var azp: String?
        get() = definedExternally
        set(value) = definedExternally
    var nonce: String?
        get() = definedExternally
        set(value) = definedExternally
    var auth_time: String?
        get() = definedExternally
        set(value) = definedExternally
    var at_hash: String?
        get() = definedExternally
        set(value) = definedExternally
    var c_hash: String?
        get() = definedExternally
        set(value) = definedExternally
    var acr: String?
        get() = definedExternally
        set(value) = definedExternally
    var amr: String?
        get() = definedExternally
        set(value) = definedExternally
    var sub_jwk: String?
        get() = definedExternally
        set(value) = definedExternally
    var cnf: String?
        get() = definedExternally
        set(value) = definedExternally
    var sid: String?
        get() = definedExternally
        set(value) = definedExternally
    @nativeGetter
    operator fun get(key: String): Any?
    @nativeSetter
    operator fun set(key: String, value: Any)
}
