package server.storage

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

@Suppress("MagicNumber")
class PostgreSqlDatabase(databaseUrl: String) : ExposedDatabase {
    private val database: Database = Database.connect(
        databaseUrl,
        "org.postgresql.Driver"
    ).also {
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_REPEATABLE_READ
    }

    override fun getDatabase(): Database = database
}
