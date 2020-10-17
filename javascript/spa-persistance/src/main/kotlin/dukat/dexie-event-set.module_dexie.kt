@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package dukat

external interface `T$14` {
    @nativeGetter
    operator fun get(eventName: String): dynamic /* String | dynamic */
    @nativeSetter
    operator fun set(eventName: String, value: String /* 'asap' */)
    @nativeSetter
    operator fun set(eventName: String, value: dynamic /* JsTuple<(f1: Function<*>, f2: Function<*>) -> Function<*>, Function<*>> */)
}

external interface DexieEventSet {
    @nativeInvoke
    operator fun invoke(eventName: String): DexieEvent
    fun addEventType(eventName: String, chainFunction: (f1: Function<*>, f2: Function<*>) -> Function<*> = definedExternally, defaultFunction: Function<*> = definedExternally): DexieEvent
    fun addEventType(events: `T$14`): DexieEvent
}
