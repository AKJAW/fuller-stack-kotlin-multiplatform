package server.storage.model

import model.toCreationTimestamp
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EntityWithCreationTimestamp(id: EntityID<Int>) : IntEntity(id) {
    var creationTimestamp by NotesTable.creationUnixTimestamp.transform(
        toColumn = { it.unix },
        toReal = { it.toCreationTimestamp() }
    )

    companion object : IntEntityClass<EntityWithCreationTimestamp>(NotesTable, EntityWithCreationTimestamp::class.java)
}
