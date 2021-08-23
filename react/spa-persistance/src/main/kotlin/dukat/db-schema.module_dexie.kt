@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package dukat

external interface DbSchema {
    @nativeGetter
    operator fun get(tableName: String): TableSchema?
    @nativeSetter
    operator fun set(tableName: String, value: TableSchema)
}
