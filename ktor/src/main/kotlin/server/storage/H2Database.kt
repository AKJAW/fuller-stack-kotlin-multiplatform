package server.storage

import com.soywiz.klock.DateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import server.storage.model.NotesTable
import java.sql.Connection

@Suppress("MagicNumber")
class H2Database : ExposedDatabase {
    private val database: Database = Database.connect(
        "jdbc:h2:mem:regular;DB_CLOSE_DELAY=-1;",
        "org.h2.Driver"
    ).also {
        initializeDatabase()
    }

    private fun initializeDatabase() {
        TransactionManager.manager.defaultIsolationLevel =
            Connection.TRANSACTION_REPEATABLE_READ

        transaction {
            createMissingTablesAndColumns(NotesTable)
            NotesTable.insert {
                it[title] = "Note 1"
                it[content] = "Content of one"
                it[creationDateTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
                it[lastModificationDateTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
            }
            NotesTable.insert {
                it[title] = "Note 2"
                it[content] = "Content of two"
                it[creationDateTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
                it[lastModificationDateTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
            }
            NotesTable.insert {
                it[title] = "Note 3"
                it[content] = "Content of three"
                it[creationDateTimestamp] = DateTime.createAdjusted(2020, 6, 5).unixMillisLong
                it[lastModificationDateTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
            }
            NotesTable.insert {
                it[title] = "Note 4"
                it[content] = "Content of four"
                it[creationDateTimestamp] = DateTime.createAdjusted(2020, 6, 7).unixMillisLong
                it[lastModificationDateTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
            }
        }
    }

    override fun getDatabase(): Database = database
}
