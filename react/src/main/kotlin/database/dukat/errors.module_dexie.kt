@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package database.dukat

typealias DexieError = Error

external interface DexieErrors {
    var OpenFailed: String /* 'OpenFailedError' */
    var VersionChange: String /* 'VersionChangeError' */
    var Schema: String /* 'SchemaError' */
    var Upgrade: String /* 'UpgradeError' */
    var InvalidTable: String /* 'InvalidTableError' */
    var MissingAPI: String /* 'MissingAPIError' */
    var NoSuchDatabase: String /* 'NoSuchDatabaseError' */
    var InvalidArgument: String /* 'InvalidArgumentError' */
    var SubTransaction: String /* 'SubTransactionError' */
    var Unsupported: String /* 'UnsupportedError' */
    var Internal: String /* 'InternalError' */
    var DatabaseClosed: String /* 'DatabaseClosedError' */
    var PrematureCommit: String /* 'PrematureCommitError' */
    var ForeignAwait: String /* 'ForeignAwaitError' */
    var Unknown: String /* 'UnknownError' */
    var Constraint: String /* 'ConstraintError' */
    var Data: String /* 'DataError' */
    var TransactionInactive: String /* 'TransactionInactiveError' */
    var ReadOnly: String /* 'ReadOnlyError' */
    var Version: String /* 'VersionError' */
    var NotFound: String /* 'NotFoundError' */
    var InvalidState: String /* 'InvalidStateError' */
    var InvalidAccess: String /* 'InvalidAccessError' */
    var Abort: String /* 'AbortError' */
    var Timeout: String /* 'TimeoutError' */
    var QuotaExceeded: String /* 'QuotaExceededError' */
    var DataClone: String /* 'DataCloneError' */
}

interface ModifyError : DexieError {
    var failures: Array<Any>
    var failedKeys: IndexableTypeArrayReadonly
    var successCount: Number
}

interface BulkError : DexieError {
    var failures: `T$19`
}

external interface DexieErrorConstructor {
    var prototype: DexieError
}

external interface ModifyErrorConstructor {
    var prototype: ModifyError
}

external interface BulkErrorConstructor {
    var prototype: BulkError
}

typealias ExceptionSet = Any

external interface `T$22` {
    var DexieError: DexieErrorConstructor
    var ModifyError: ModifyErrorConstructor
    var BulkError: BulkErrorConstructor
}