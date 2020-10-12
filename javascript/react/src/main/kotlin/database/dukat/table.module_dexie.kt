@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

import kotlin.js.Json
import kotlin.js.Promise

external interface `T$10`<TKey> {
    var key: Any
    var primaryKey: TKey
}

external interface `T$11`<B> {
    var allKeys: B
}

external interface `T$12` {
    var allKeys: Boolean
}

external interface Table<T, TKey> {
    var db: Database
    var name: String
    var schema: TableSchema
    var hook: TableHooks<T, TKey>
    var core: DBCoreTable
    fun get(key: TKey): Promise<T?>
    fun <R> get(key: TKey, thenShortcut: ThenShortcut<T?, R>): Promise<R>
    fun get(equalityCriterias: Json): Promise<T?>
    fun <R> get(equalityCriterias: Json, thenShortcut: ThenShortcut<T?, R>): Promise<R>
    fun where(index: String): WhereClause<T, TKey>
    fun where(index: Array<String>): WhereClause<T, TKey>
    fun where(equalityCriterias: Json): Collection<T, TKey>
    fun filter(fn: (obj: T) -> Boolean): Collection<T, TKey>
    fun count(): Promise<Number>
    fun <R> count(thenShortcut: ThenShortcut<Number, R>): Promise<R>
    fun offset(n: Number): Collection<T, TKey>
    fun limit(n: Number): Collection<T, TKey>
    fun each(callback: (obj: T, cursor: `T$10`<TKey>) -> Any): Promise<Unit>
    fun toArray(): Promise<Array<T>>
    fun <R> toArray(thenShortcut: ThenShortcut<Array<T>, R>): Promise<R>
    fun toCollection(): Collection<T, TKey>
    fun orderBy(index: String): Collection<T, TKey>
    fun orderBy(index: Array<String>): Collection<T, TKey>
    fun reverse(): Collection<T, TKey>
    fun mapToClass(constructor: Function<*>): Function<*>
    fun add(item: T, key: TKey = definedExternally): Promise<TKey>
    fun update(key: TKey, changes: Json): Promise<Number>
    fun update(key: T, changes: Json): Promise<Number>
    fun put(item: T, key: TKey = definedExternally): Promise<TKey>
    fun delete(key: TKey): Promise<Unit>
    fun clear(): Promise<Unit>
    fun bulkGet(keys: Array<TKey>): Promise<Array<T?>>
    fun <B : Boolean> bulkAdd(items: Array<T>, keys: IndexableTypeArrayReadonly, options: `T$11`<B>): Promise<Any>
    fun <B : Boolean> bulkAdd(items: Array<T>, options: `T$11`<B>): Promise<Any>
    fun bulkAdd(items: Array<T>, keys: IndexableTypeArrayReadonly = definedExternally, options: `T$12` = definedExternally): Promise<TKey>
    fun <B : Boolean> bulkPut(items: Array<T>, keys: IndexableTypeArrayReadonly, options: `T$11`<B>): Promise<Any>
    fun <B : Boolean> bulkPut(items: Array<T>, options: `T$11`<B>): Promise<Any>
    fun bulkPut(items: Array<T>, keys: IndexableTypeArrayReadonly = definedExternally, options: `T$12` = definedExternally): Promise<TKey>
    fun bulkDelete(keys: IndexableTypeArrayReadonly): Promise<Unit>
}

external interface Table__0 :
    Table<Any, dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | IndexableTypeArrayReadonly */>
