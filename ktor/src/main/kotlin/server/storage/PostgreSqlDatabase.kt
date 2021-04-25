package server.storage

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import server.storage.model.NotesTable

@Suppress("MagicNumber")
class PostgreSqlDatabase(databaseUrl: String) : ExposedDatabase {
    private val database: Database = Database.connect(hikari(databaseUrl)).apply {
        transaction {
            SchemaUtils.createMissingTablesAndColumns(NotesTable)
        }
    }

    private fun hikari(databaseUrl: String): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = databaseUrl
        config.maximumPoolSize = 3
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    override fun getDatabase(): Database = database
}
