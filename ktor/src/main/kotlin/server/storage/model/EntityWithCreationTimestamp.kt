package server.storage.model

import model.CreationTimestamp
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityWithCreationTimestamp(id: EntityID<Int>) : IntEntity(id) {
    var creationTimestamp by NotesTable.creationUnixTimestamp.transform(
        toColumn = { it.unix },
        toReal = { CreationTimestamp(it) }
    )

    companion object : IntEntityClass<EntityWithCreationTimestamp>(NotesTable, EntityWithCreationTimestamp::class.java)
}
