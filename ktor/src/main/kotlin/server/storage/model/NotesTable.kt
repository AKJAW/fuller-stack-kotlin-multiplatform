package server.storage.model

import org.jetbrains.exposed.dao.id.IntIdTable

@Suppress("MagicNumber")
object NotesTable : IntIdTable("Notes") {
    val title = varchar("title", 255)
    val content = varchar("content", Int.MAX_VALUE)
    val creationDateTimestamp = long("creationDateTimestamp")
}
