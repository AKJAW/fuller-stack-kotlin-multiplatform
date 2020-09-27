package server.storage.model

import model.toLastModificationTimestamp
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityWithLastModificationTimestamp(id: EntityID<Int>) : IntEntity(id) {
    var lastModificationTimestamp by NotesTable.lastModificationUnixTimestamp.transform(
        toColumn = { it.unix },
        toReal = { it.toLastModificationTimestamp() }
    )

    companion object : IntEntityClass<EntityWithLastModificationTimestamp>(NotesTable)
}
