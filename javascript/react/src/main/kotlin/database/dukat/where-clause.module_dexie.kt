@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.ArrayBufferView
import org.khronos.webgl.DataView
import kotlin.js.Date

external interface `T$15` {
    operator fun get(key: String): Any
    operator fun set(key: String, value: Any)
}

external interface `T$16` {
    var includeLowers: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var includeUppers: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface WhereClause<T, TKey> {
    fun above(key: Any): Collection<T, TKey>
    fun aboveOrEqual(key: Any): Collection<T, TKey>
    fun anyOf(keys: Array<dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | IndexableTypeArrayReadonly */>): Collection<T, TKey>
    fun anyOf(vararg keys: dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | IndexableTypeArrayReadonly */): Collection<T, TKey>
    fun anyOfIgnoreCase(keys: Array<String>): Collection<T, TKey>
    fun anyOfIgnoreCase(vararg keys: String): Collection<T, TKey>
    fun below(key: Any): Collection<T, TKey>
    fun belowOrEqual(key: Any): Collection<T, TKey>
    fun between(lower: Any, upper: Any, includeLower: Boolean = definedExternally, includeUpper: Boolean = definedExternally): Collection<T, TKey>
    fun equals(key: String): Collection<T, TKey>
    fun equals(key: Number): Collection<T, TKey>
    fun equals(key: Date): Collection<T, TKey>
    fun equals(key: ArrayBuffer): Collection<T, TKey>
    fun equals(key: ArrayBufferView): Collection<T, TKey>
    fun equals(key: DataView): Collection<T, TKey>
    fun equals(key: Array<Array<Unit>>): Collection<T, TKey>
    fun equals(key: IndexableTypeArrayReadonly): Collection<T, TKey>
    fun equalsIgnoreCase(key: String): Collection<T, TKey>
    fun inAnyRange(ranges: Array<`T$15`>, options: `T$16` = definedExternally): Collection<T, TKey>
    fun startsWith(key: String): Collection<T, TKey>
    fun startsWithAnyOf(prefixes: Array<String>): Collection<T, TKey>
    fun startsWithAnyOf(vararg prefixes: String): Collection<T, TKey>
    fun startsWithIgnoreCase(key: String): Collection<T, TKey>
    fun startsWithAnyOfIgnoreCase(prefixes: Array<String>): Collection<T, TKey>
    fun startsWithAnyOfIgnoreCase(vararg prefixes: String): Collection<T, TKey>
    fun noneOf(keys: Array<dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | IndexableTypeArrayReadonly */>): Collection<T, TKey>
    fun notEqual(key: String): Collection<T, TKey>
    fun notEqual(key: Number): Collection<T, TKey>
    fun notEqual(key: Date): Collection<T, TKey>
    fun notEqual(key: ArrayBuffer): Collection<T, TKey>
    fun notEqual(key: ArrayBufferView): Collection<T, TKey>
    fun notEqual(key: DataView): Collection<T, TKey>
    fun notEqual(key: Array<Array<Unit>>): Collection<T, TKey>
    fun notEqual(key: IndexableTypeArrayReadonly): Collection<T, TKey>
}

external interface WhereClause__0 :
    WhereClause<Any, dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | IndexableTypeArrayReadonly */>
