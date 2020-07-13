package server.storage

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> queryDatabase(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
    block()
}
