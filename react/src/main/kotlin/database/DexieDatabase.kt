package database

import database.dukat.DexieIndex
import network.NoteSchema
import kotlin.js.json

object DexieDatabase {
    private const val tableName = "testTable"
    val db = (js("new Dexie('test2')") as DexieIndex).apply {
        version(1).stores(
            json(
                tableName to "++localId,title,content,lastModificationTimestamp,creationTimestamp,hasSyncFailed,wasDeleted"
            )
        )
    }
    val noteTable: DexieIndex.Table<NoteSchema, Int> = db.table(tableName)
}



