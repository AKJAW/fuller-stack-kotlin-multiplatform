package server.storage

import org.jetbrains.exposed.sql.Database

interface ExposedDatabase {

    fun initializeDatabase()

    fun getDatabase(): Database
}
