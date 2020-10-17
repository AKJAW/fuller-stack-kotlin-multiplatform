@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package dukat

import kotlin.js.Json

external interface Version {
    fun stores(schema: Json): Version
    fun upgrade(fn: (trans: Transaction) -> Unit): Version
}
