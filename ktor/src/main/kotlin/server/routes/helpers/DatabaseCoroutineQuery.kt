package server.routes.helpers

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> queryDatabase(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
    block()
}