
import react.RClass
import react.RProps

@JsModule("@auth0/auth0-react")
@JsNonModule
external val Auth0Module: dynamic

val Auth0Provider: RClass<Auth0ProviderProps> = Auth0Module.Auth0Provider

interface Auth0ProviderProps : RProps {
    var domain: String
    var clientId: String
    var audience: String
    var redirectUri: String
    var onRedirectCallback: (appState: AppState) -> Unit
}

val UseAuth0: () -> Auth0ContextInterface = Auth0Module.useAuth0
//TODO clean up unneeded dukat generated classes

// fun RBuilder.auth0Provider(
//    domain: String,
//    clientId: String,
//    audience: String,
//    redirectUri: String,
//    onRedirectCallback: (appState: AppState) -> Unit,
//    handler: RHandler<Auth0ProviderProps>
// ): ReactElement = child(Auth0Provider::class) {
// //        attrs.domain = domain
// //        attrs.clientId = clientId
// //        attrs.audience = audience
// //        attrs.redirectUri = redirectUri
// //        attrs.onRedirectCallback = onRedirectCallback
//        handler()
//    }
