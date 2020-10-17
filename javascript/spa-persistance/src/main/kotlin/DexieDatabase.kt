import dukat.DexieIndex
import kotlin.js.json

@Suppress("MaxLineLength")
object DexieDatabase {
    private const val tableName = "testTable"
    val db = (js("new Dexie('test8')") as DexieIndex).apply {
        version(1).stores(
            json(
                tableName to "++localId,creationTimestamp,title,content,lastModificationTimestamp,hasSyncFailed,wasDeleted"
            )
        )
    }
    val noteTable: DexieIndex.Table<DexieNoteEntity, Int> = db.table(tableName)
}
