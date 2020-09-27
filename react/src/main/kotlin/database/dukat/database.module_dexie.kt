@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

import kotlin.js.Promise

external interface Database {
    var name: String
    var tables: Array<Table__0>
    fun <T, TKey> table(tableName: String): Table<T, TKey>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, scope: () -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, scope: () -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, table3: Table__0, scope: () -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, table3: Table__0, table4: Table__0, scope: () -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, table3: Table__0, table4: Table__0, table5: Table__0, scope: () -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, tables: Array<Table__0>, scope: () -> dynamic): Promise<U>
}
