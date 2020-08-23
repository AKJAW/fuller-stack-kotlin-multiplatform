@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

external interface DexieEvent {
    var subscribers: Array<Function<*>>
    fun fire(vararg args: Any): Any
    fun subscribe(fn: (args: Any) -> Any)
    fun unsubscribe(fn: (args: Any) -> Any)
}