@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

external interface CreatingHookContext<T, Key> {
    var onsuccess: ((primKey: Key) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var onerror: ((err: Any) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface UpdatingHookContext<T, Key> {
    var onsuccess: ((updatedObj: T) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var onerror: ((err: Any) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DeletingHookContext<T, Key> {
    var onsuccess: (() -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
    var onerror: ((err: Any) -> Unit)?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TableHooks<T, TKey> : DexieEventSet {
    @nativeInvoke
    operator fun invoke(eventName: String /* 'creating' */, subscriber: (self: CreatingHookContext<T, TKey>, primKey: TKey, obj: T, transaction: Transaction) -> Any)
    @nativeInvoke
    operator fun invoke(eventName: String /* 'reading' */, subscriber: (obj: T) -> dynamic)
    @nativeInvoke
    operator fun invoke(eventName: String /* 'updating' */, subscriber: (self: UpdatingHookContext<T, TKey>, modifications: Any, primKey: TKey, obj: T, transaction: Transaction) -> Any)
    @nativeInvoke
    operator fun invoke(eventName: String /* 'deleting' */, subscriber: (self: DeletingHookContext<T, TKey>, primKey: TKey, obj: T, transaction: Transaction) -> Any)
    var creating: DexieEvent
    var reading: DexieEvent
    var updating: DexieEvent
    var deleting: DexieEvent
}
