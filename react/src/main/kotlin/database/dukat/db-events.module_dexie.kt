@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

external interface DexieOnReadyEvent {
    fun subscribe(fn: () -> Any, bSticky: Boolean)
    fun unsubscribe(fn: () -> Any)
    fun fire(): Any
}

external interface DexieVersionChangeEvent {
    fun subscribe(fn: (event: IDBVersionChangeEvent) -> Any)
    fun unsubscribe(fn: (event: IDBVersionChangeEvent) -> Any)
    fun fire(event: IDBVersionChangeEvent): Any
}

external interface DexiePopulateEvent {
    fun subscribe(fn: (trans: Transaction) -> Any)
    fun unsubscribe(fn: (trans: Transaction) -> Any)
    fun fire(trans: Transaction): Any
}

external interface DbEvents : DexieEventSet {
    @nativeInvoke
    operator fun invoke(eventName: String /* 'ready' */, subscriber: () -> Any, bSticky: Boolean = definedExternally)
    @nativeInvoke
    operator fun invoke(eventName: String /* 'populate' */, subscriber: (trans: Transaction) -> Any)
    @nativeInvoke
    operator fun invoke(eventName: String /* 'blocked' | 'versionchange' */, subscriber: (event: IDBVersionChangeEvent) -> Any)
    var ready: DexieOnReadyEvent
    var populate: DexiePopulateEvent
    var blocked: DexieEvent
    var versionchange: DexieVersionChangeEvent
}
