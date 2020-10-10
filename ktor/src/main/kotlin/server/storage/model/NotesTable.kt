package server.storage.model

import org.jetbrains.exposed.dao.id.IntIdTable

@Suppress("MagicNumber")
object NotesTable : IntIdTable("Notes") {
    val userId = varchar("userId", 2560)
    val title = varchar("title", 255)
    val content = varchar("content", 10485760)
    val lastModificationUnixTimestamp = long("lastModificationUnixTimestamp")
    val creationUnixTimestamp = long("creationUnixTimestamp")
    val wasDeleted = bool("wasDeleted")
}
