package server.storage.model

import model.CreationTimestamp
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CreationTimestampEntity(id: EntityID<Int>) : IntEntity(id) {
    var creationDateTimestamp by NotesTable.creationDateTimestamp.transform(
        toColumn = { it.unix },
        toReal = { CreationTimestamp(it) }
    )

    companion object : IntEntityClass<CreationTimestampEntity>(NotesTable, CreationTimestampEntity::class.java)
}
