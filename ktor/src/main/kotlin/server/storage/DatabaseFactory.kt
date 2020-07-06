package server.storage

class DatabaseFactory {
    fun create(): ExposedDatabase {
        val databaseUrl: String? = System.getenv("JDBC_DATABASE_URL")
        println("databaseUrl $databaseUrl")
        return when (databaseUrl) {
            null -> H2Database()
            else -> PostgreSqlDatabase(databaseUrl)
        }
    }
}
