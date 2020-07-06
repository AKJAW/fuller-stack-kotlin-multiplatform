package server.storage

import org.jetbrains.exposed.sql.Database

interface ExposedDatabase {

    fun getDatabase(): Database
}
