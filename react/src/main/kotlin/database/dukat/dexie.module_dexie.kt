@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

import kotlin.js.Promise

external interface `T$2` {
    @nativeGetter
    operator fun get(name: String): Table<Any, dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | database.dukat.IndexableTypeArrayReadonly */>?
    @nativeSetter
    operator fun set(name: String, value: Table<Any, dynamic /* String | Number | Date | ArrayBuffer | ArrayBufferView | DataView | Array<Array<Unit>> | database.dukat.IndexableTypeArrayReadonly */>)
}

external interface `T$3` {
    var stack: Any
}

external interface `T$4` {
    var stack: Any
    var name: String
}

external interface `T$5` {
    var prototype: Table__0
}

external interface `T$6` {
    var prototype: WhereClause__0
}

external interface `T$7` {
    var prototype: Version
}

external interface `T$8` {
    var prototype: Transaction
}

external interface `T$9` {
    var prototype: Collection__0
}

@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
external interface DexieInterface : Database {
    override var name: String
    override var tables: Array<Table__0>
    var verno: Number
    var _allTables: `T$2`
    var core: DBCore
    var _createTransaction: (self: DexieInterface, mode: String /* "readonly" | "readwrite" | "versionchange" */, storeNames: ArrayLike<String>, dbschema: DbSchema, parentTransaction: Transaction?) -> Transaction
    var _dbSchema: DbSchema
    fun version(versionNumber: Number): Version
    var on: DbEvents
    fun open(): Promise<DexieInterface>
    override fun <T, TKey> table(tableName: String): Table<T, TKey>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: String, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: String, table2: String, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, table3: Table__0, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: String, table2: String, table3: String, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, table3: Table__0, table4: Table__0, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: String, table2: String, table3: String, table4: String, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: Table__0, table2: Table__0, table3: Table__0, table4: Table__0, table5: Table__0, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, table: String, table2: String, table3: String, table4: String, table5: String, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, tables: Array<Table__0>, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun <U> transaction(mode: String /* 'readonly' | 'readwrite' | 'r' | 'r!' | 'r?' | 'rw' | 'rw!' | 'rw?' */, tables: Array<String>, scope: (trans: Transaction) -> dynamic): Promise<U>
    fun close()
    fun delete(): Promise<Unit>
    fun isOpen(): Boolean
    fun hasBeenClosed(): Boolean
    fun hasFailed(): Boolean
    fun dynamicallyOpened(): Boolean
    fun backendDB(): IDBDatabase
    fun use(middleware: Middleware<DBCore>): DexieInterface /* this */
    fun unuse(__0: Middleware<DBCore>): DexieInterface /* this */
    fun unuse(__0: `T$4`): DexieInterface /* this */
    var table: `T$5`
    var whereClause: `T$6`
    var version: `T$7`
    var transaction: `T$8`
    var collection: `T$9`
//    interface database.dukat.Table<T, Key> : _Table<T, Key>
//    interface database.dukat.Collection<T, Key> : _Collection<T, Key>
}
