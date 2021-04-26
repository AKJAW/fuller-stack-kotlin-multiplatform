@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package dukat

external interface `T$13` {
    @nativeGetter
    operator fun get(name: String): IndexSpec?
    @nativeSetter
    operator fun set(name: String, value: IndexSpec)
}

external interface TableSchema {
    var name: String
    var primKey: IndexSpec
    var indexes: Array<IndexSpec>
    var mappedClass: Function<*>
    var idxByName: `T$13`
    var readHook: ((x: Any) -> Any)?
        get() = definedExternally
        set(value) = definedExternally
}
