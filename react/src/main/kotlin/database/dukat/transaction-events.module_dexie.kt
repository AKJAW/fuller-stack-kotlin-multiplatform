@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

external interface TransactionEvents : DexieEventSet {
    @nativeInvoke
    operator fun invoke(eventName: String /* 'complete' | 'abort' */, subscriber: () -> Any)
    @nativeInvoke
    operator fun invoke(eventName: String /* 'error' */, subscriber: (error: Any) -> Any)
    var complete: DexieEvent
    var abort: DexieEvent
    var error: DexieEvent
}
