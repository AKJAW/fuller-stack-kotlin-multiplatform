@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

external interface Transaction {
    var db: Database
    var active: Boolean
    var mode: String /* "readonly" | "readwrite" | "versionchange" */
    var storeNames: Array<String>
    var parent: Transaction?
        get() = definedExternally
        set(value) = definedExternally
    var on: TransactionEvents
    fun abort()
    fun table(tableName: String): Table<Any, Any>
    fun <T> table(tableName: String): Table<T, Any>
    fun <T, Key> table(tableName: String): Table<T, Key>
}