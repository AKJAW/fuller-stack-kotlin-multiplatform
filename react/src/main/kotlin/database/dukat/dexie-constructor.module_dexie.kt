@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

import kotlin.js.Promise

external interface `T$0` {
    var open: Function<*>
}

external interface `T$1` {
    var bound: Function<*>
    var lowerBound: Function<*>
    var upperBound: Function<*>
}

external interface DexieOptions {
    var addons: Array<(db: DexieInterface) -> Unit>?
        get() = definedExternally
        set(value) = definedExternally
    var autoOpen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var indexedDB: `T$0`?
        get() = definedExternally
        set(value) = definedExternally
    var IDBKeyRange: `T$1`?
        get() = definedExternally
        set(value) = definedExternally
    var allowEmptyDB: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DexieConstructor {
    fun new(databaseName: String, options: DexieOptions?): DexieInterface
    var prototype: DexieInterface
    var addons: Array<(db: DexieInterface) -> Unit>
    var version: Number
    var semVer: String
    var currentTransaction: Transaction
    fun <T> waitFor(promise: PromiseLike<T>, timeoutMilliseconds: Number = definedExternally): Promise<T>
    fun <T> waitFor(promise: T, timeoutMilliseconds: Number = definedExternally): Promise<T>
    fun getDatabaseNames(): Promise<Array<String>>
    fun <R> getDatabaseNames(thenShortcut: ThenShortcut<Array<String>, R>): Promise<R>
    fun <U> vip(scopeFunction: () -> U): U
    fun <U> ignoreTransaction(fn: () -> U): U
    fun <F> override(origFunc: F, overridedFactory: (fn: Any) -> Any): F
    fun getByKeyPath(obj: Any, keyPath: String): Any
    fun setByKeyPath(obj: Any, keyPath: String, value: Any)
    fun delByKeyPath(obj: Any, keyPath: String)
    fun <T> shallowClone(obj: T): T
    fun <T> deepClone(obj: T): T
    fun asap(fn: Function<*>)
    var maxKey: dynamic /* Array<Array<Unit>> | String */
        get() = definedExternally
        set(value) = definedExternally
    var minKey: Number
    fun exists(dbName: String): Promise<Boolean>
    fun delete(dbName: String): Promise<Unit>
    var dependencies: DexieDOMDependencies
    var default: DexieInterface
    var Events: (ctx: Any) -> DexieEventSet
    var errnames: Any
}
