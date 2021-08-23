@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package dukat

external interface DexieDOMDependencies {
    var indexedDB: IDBFactory
    var IDBKeyRange: Any
}
