// @file:JsModule("@auth0/auth0-spa-js")
// @file:JsNonModule
// @file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
// "CONFLICTING_OVERLOADS")
//
// import kotlin.js.Promise
//
// @JsName("default")
// open external class Auth0Client(options: Auth0ClientOptions) {
//    open var options: Any
//    open var cache: Any
//    open var transactionManager: Any
//    open var customOptions: Any
//    open var domainUrl: Any
//    open var tokenIssuer: Any
//    open var defaultScope: Any
//    open var scope: Any
//    open var cookieStorage: Any
//    open var cacheLocation: String /* 'memory' | 'localstorage' */
//    open var worker: Any
//    open var _url: Any
//    open var _getParams: Any
//    open var _authorizeUrl: Any
//    open var _verifyIdToken: Any
//    open var _parseNumber: Any
//    open fun buildAuthorizeUrl(options: RedirectLoginOptions = definedExternally): Promise<String>
//    open fun loginWithPopup(options: PopupLoginOptions = definedExternally, config:
//    PopupConfigOptions = definedExternally): Promise<Unit>
//    open fun getUser(options: GetUserOptions = definedExternally): Promise<Any>
//    open fun getIdTokenClaims(options: GetIdTokenClaimsOptions = definedExternally): Promise<Any>
//    open fun loginWithRedirect(options: RedirectLoginOptions = definedExternally): Promise<Unit>
//    open fun handleRedirectCallback(url: String = definedExternally): Promise<RedirectLoginResult>
//    open fun checkSession(options: GetTokenSilentlyOptions = definedExternally): Promise<Unit>
//    open fun getTokenSilently(options: GetTokenSilentlyOptions = definedExternally): Promise<Any>
//    open fun getTokenWithPopup(options: GetTokenWithPopupOptions = definedExternally, config:
//    PopupConfigOptions = definedExternally): Promise<String>
//    open fun isAuthenticated(): Promise<Boolean>
//    open fun logout(options: LogoutOptions = definedExternally)
//    open var _getTokenFromIFrame: Any
//    open var _getTokenUsingRefreshToken: Any
// }
