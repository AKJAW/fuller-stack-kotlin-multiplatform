@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package dukat

import kotlin.js.Json
import kotlin.js.Promise

external interface `T$17`<TKey> {
    var key: dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | dukat.IndexableTypeArrayReadonly */
        get() = definedExternally
        set(value) = definedExternally
    var primaryKey: TKey
}

external interface `T$18`<T> {
    var value: T
}

external interface Collection<T, TKey> {
    fun and(filter: (x: T) -> Boolean): Collection<T, TKey>
    fun clone(props: Any = definedExternally): Collection<T, TKey>
    fun count(): Promise<Number>
    fun <R> count(thenShortcut: ThenShortcut<Number, R>): Promise<R>
    fun distinct(): Collection<T, TKey>
    fun each(callback: (obj: T, cursor: `T$17`<TKey>) -> Any): Promise<Unit>
    fun eachKey(callback: (key: dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | dukat.IndexableTypeArrayReadonly */, cursor: `T$17`<TKey>) -> Any): Promise<Unit>
    fun eachPrimaryKey(callback: (key: TKey, cursor: `T$17`<TKey>) -> Any): Promise<Unit>
    fun eachUniqueKey(callback: (key: dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | dukat.IndexableTypeArrayReadonly */, cursor: `T$17`<TKey>) -> Any): Promise<Unit>
    fun filter(filter: (x: T) -> Boolean): Collection<T, TKey>
    fun first(): Promise<T?>
    fun <R> first(thenShortcut: ThenShortcut<T?, R>): Promise<R>
    fun keys(): Promise<IndexableTypeArray>
    fun <R> keys(thenShortcut: ThenShortcut<IndexableTypeArray, R>): Promise<R>
    fun primaryKeys(): Promise<Array<TKey>>
    fun <R> primaryKeys(thenShortcut: ThenShortcut<Array<TKey>, R>): Promise<R>
    fun last(): Promise<T?>
    fun <R> last(thenShortcut: ThenShortcut<T?, R>): Promise<R>
    fun limit(n: Number): Collection<T, TKey>
    fun offset(n: Number): Collection<T, TKey>
    fun or(indexOrPrimayKey: String): WhereClause<T, TKey>
    fun raw(): Collection<T, TKey>
    fun reverse(): Collection<T, TKey>
    fun sortBy(keyPath: String): Promise<Array<T>>
    fun <R> sortBy(keyPath: String, thenShortcut: ThenShortcut<Array<T>, R>): Promise<R>
    fun toArray(): Promise<Array<T>>
    fun <R> toArray(thenShortcut: ThenShortcut<Array<T>, R>): Promise<R>
    fun uniqueKeys(): Promise<IndexableTypeArray>
    fun <R> uniqueKeys(thenShortcut: ThenShortcut<IndexableTypeArray, R>): Promise<R>
    fun until(filter: (value: T) -> Boolean, includeStopEntry: Boolean = definedExternally): Collection<T, TKey>
    fun delete(): Promise<Number>
    fun modify(changeCallback: (obj: T, ctx: `T$18`<T>) -> dynamic): Promise<Number>
    fun modify(changes: Json): Promise<Number>
}

external interface Collection__0 :
    Collection<Any, dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | dukat.IndexableTypeArrayReadonly */>
