@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "MatchingDeclarationName"
)

external interface WithAuthenticationRequiredOptions {
    var returnTo: dynamic /* String? | (() -> String)? */
        get() = definedExternally
        set(value) = definedExternally
    var onRedirecting: (() -> dynamic)?
        get() = definedExternally
        set(value) = definedExternally
    var loginOptions: RedirectLoginOptions?
        get() = definedExternally
        set(value) = definedExternally
}
