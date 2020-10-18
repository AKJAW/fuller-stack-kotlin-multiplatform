@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "MatchingDeclarationName"
)

import kotlin.js.Promise

@Suppress("MatchingDeclarationName")
external interface Auth0ContextInterface : AuthState {
    var getAccessTokenSilently: (options: GetTokenSilentlyOptions) -> Promise<String>
    var getAccessTokenWithPopup: (options: GetTokenWithPopupOptions) -> Promise<String>
    var getIdTokenClaims: (options: GetIdTokenClaimsOptions) -> Promise<IdToken>
    var loginWithRedirect: (options: RedirectLoginOptions?) -> Promise<Unit>
    var loginWithPopup: (options: PopupLoginOptions, config: PopupConfigOptions) -> Promise<Unit>
    var logout: (options: LogoutOptions?) -> Unit
}
