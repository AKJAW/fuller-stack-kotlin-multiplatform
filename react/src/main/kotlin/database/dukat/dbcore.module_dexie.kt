@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

import kotlin.js.Promise

external enum class DBCoreRangeType {
    Equal /* = 1 */,
    Range /* = 2 */,
    Any /* = 3 */,
    Never /* = 4 */
}

external interface DBCoreKeyRange {
    var type: DBCoreRangeType
    var lower: Any
    var lowerOpen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var upper: Any
    var upperOpen: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DBCoreTransaction {
    fun abort()
}

external interface DBCoreTransactionRequest {
    var tables: Array<String>
    var mode: String /* 'readonly' | 'readwrite' */
}

external interface `T$19` {
    @nativeGetter
    operator fun get(operationNumber: Number): Error?
    @nativeSetter
    operator fun set(operationNumber: Number, value: Error)
}

external interface DBCoreMutateResponse {
    var numFailures: Number
    var failures: `T$19`
    var lastResult: Any
    var results: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DBCoreAddRequest {
    var type: String /* 'add' */
    var trans: DBCoreTransaction
    var values: Array<Any>
    var keys: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
    var wantResults: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DBCorePutRequest {
    var type: String /* 'put' */
    var trans: DBCoreTransaction
    var values: Array<Any>
    var keys: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
    var wantResults: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface DBCoreDeleteRequest {
    var type: String /* 'delete' */
    var trans: DBCoreTransaction
    var keys: Array<Any>
}

external interface DBCoreDeleteRangeRequest {
    var type: String /* 'deleteRange' */
    var trans: DBCoreTransaction
    var range: DBCoreKeyRange
}

external interface DBCoreGetManyRequest {
    var trans: DBCoreTransaction
    var keys: Array<Any>
}

external interface DBCoreGetRequest {
    var trans: DBCoreTransaction
    var key: Any
}

external interface DBCoreQuery {
    var index: DBCoreIndex
    var range: DBCoreKeyRange
}

external interface DBCoreQueryRequest {
    var trans: DBCoreTransaction
    var values: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var limit: Number?
        get() = definedExternally
        set(value) = definedExternally
    var query: DBCoreQuery
}

external interface DBCoreQueryResponse {
    var result: Array<Any>
}

external interface DBCoreOpenCursorRequest {
    var trans: DBCoreTransaction
    var values: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var unique: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var reverse: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var query: DBCoreQuery
}

external interface DBCoreCountRequest {
    var trans: DBCoreTransaction
    var query: DBCoreQuery
}

external interface DBCoreCursor {
    var trans: DBCoreTransaction
    var key: Any
    var primaryKey: Any
    var value: Any?
        get() = definedExternally
        set(value) = definedExternally
    var done: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    fun `continue`(key: Any = definedExternally)
    fun continuePrimaryKey(key: Any, primaryKey: Any)
    fun advance(count: Number)
    fun start(onNext: () -> Unit): Promise<Any>
    fun stop(value: Any = definedExternally)
    fun stop(value: Promise<Any> = definedExternally)
    fun next(): Promise<DBCoreCursor>
    fun fail(error: Error)
}

external interface DBCoreSchema {
    var name: String
    var tables: Array<DBCoreTableSchema>
}

external interface DBCoreTableSchema {
    var name: String
    var primaryKey: DBCoreIndex
    var indexes: Array<DBCoreIndex>
    var getIndexByKeyPath: (keyPath: dynamic /* String? | Array<String>? */) -> DBCoreIndex?
}

external interface DBCoreIndex {
    var name: String?
    var isPrimaryKey: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var outbound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var compound: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var keyPath: dynamic /* String? | Array<String>? */
        get() = definedExternally
        set(value) = definedExternally
    var autoIncrement: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var unique: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var multiEntry: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var extractKey: (value: Any) -> Any
}

external interface DBCore {
    var stack: String /* "dbcore" */
    fun transaction(req: DBCoreTransactionRequest): DBCoreTransaction
    fun cmp(a: Any, b: Any): Number
    var MIN_KEY: Any
    var MAX_KEY: Any
    var schema: DBCoreSchema
    fun table(name: String): DBCoreTable
}

external interface DBCoreTable {
    var name: String
    var schema: DBCoreTableSchema
    fun mutate(req: DBCoreAddRequest): Promise<DBCoreMutateResponse>
    fun mutate(req: DBCorePutRequest): Promise<DBCoreMutateResponse>
    fun mutate(req: DBCoreDeleteRequest): Promise<DBCoreMutateResponse>
    fun mutate(req: DBCoreDeleteRangeRequest): Promise<DBCoreMutateResponse>
    fun get(req: DBCoreGetRequest): Promise<Any>
    fun getMany(req: DBCoreGetManyRequest): Promise<Array<Any>>
    fun query(req: DBCoreQueryRequest): Promise<DBCoreQueryResponse>
    fun openCursor(req: DBCoreOpenCursorRequest): Promise<DBCoreCursor?>
    fun count(req: DBCoreCountRequest): Promise<Number>
}
