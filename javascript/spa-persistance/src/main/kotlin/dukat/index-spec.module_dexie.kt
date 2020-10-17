@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package dukat

external interface IndexSpec {
    var name: String
    var keyPath: dynamic /* String? | Array<String>? */
        get() = definedExternally
        set(value) = definedExternally
    var unique: Boolean?
    var multi: Boolean?
    var auto: Boolean?
    var compound: Boolean?
    var src: String
}
