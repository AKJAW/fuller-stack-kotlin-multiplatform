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
                it[userId] = "LrviSpxuDVsE0263vbgcvRPycCgvxPlB@clients"
                it[title] = "Note 1"
                it[content] = "Content of one"
                it[creationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 1).unixMillisLong
                it[lastModificationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 1).unixMillisLong
                it[wasDeleted] = false
            }
            NotesTable.insert {
                it[userId] = "LrviSpxuDVsE0263vbgcvRPycCgvxPlB@clients"
                it[title] = "Note 2"
                it[content] = "Content of two"
                it[creationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
                it[lastModificationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
                it[wasDeleted] = false
            }
            NotesTable.insert {
                it[userId] = "2"
                it[title] = "Note 3"
                it[content] = "Content of three"
                it[creationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 5).unixMillisLong
                it[lastModificationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
                it[wasDeleted] = false
            }
            NotesTable.insert {
                it[userId] = "2"
                it[title] = "Note 4"
                it[content] = "Content of four"
                it[creationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 7).unixMillisLong
                it[lastModificationUnixTimestamp] = DateTime.createAdjusted(2020, 6, 2).unixMillisLong
                it[wasDeleted] = false
            }
        }
    }

    override fun getDatabase(): Database = database
}
