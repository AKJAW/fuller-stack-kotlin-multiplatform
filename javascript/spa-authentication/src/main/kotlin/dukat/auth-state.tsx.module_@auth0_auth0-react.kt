@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS"
)

typealias User = Any

external interface AuthState {
    var error: Error?
        get() = definedExternally
        set(value) = definedExternally
    var isAuthenticated: Boolean
    var isLoading: Boolean
    var user: User?
        get() = definedExternally
        set(value) = definedExternally
}
