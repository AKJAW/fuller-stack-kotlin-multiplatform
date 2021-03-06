import database.NoteDao
import database.NoteEntity
import dukat.Transaction
import dukat.delete
import feature.AddNotePayload
import feature.UpdateNotePayload
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import model.CreationTimestamp
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import kotlin.js.json

@Suppress("TooManyFunctions")
class DexieNoteDao : NoteDao {

    private val deferredNotesStateFlow: Deferred<List<NoteEntity>> = GlobalScope.async {
        DexieDatabase.getNotes()
    }

    private lateinit var notesStateFlow: MutableStateFlow<List<NoteEntity>>

    var isInitialized: Boolean = false
        private set

    suspend fun initialize() {
        notesStateFlow = MutableStateFlow(deferredNotesStateFlow.await())
        DexieDatabase.db.on("changes") { changes: Transaction ->
            console.log(changes)
            updateNotesFlow()
        }
        isInitialized = true
    }

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return notesStateFlow
    }

    private fun updateNotesFlow() {
        GlobalScope.launch {
            notesStateFlow.value = DexieDatabase.getNotes()
            console.log(notesStateFlow.value)
        }
    }

    private suspend fun DexieDatabase.getNotes(): List<NoteEntity> =
        noteTable
            .orderBy("creationTimestamp")
            .reverse()
            .toArray()
            .await()
            .map(::toDomainEntity)
            .also {
                console.log(it)
            }

    private fun toDomainEntity(dexieNoteEntity: DexieNoteEntity): NoteEntity {
        return NoteEntity(
            localId = dexieNoteEntity.localId ?: -1,
            title = dexieNoteEntity.title,
            content = dexieNoteEntity.content,
            lastModificationTimestamp = dexieNoteEntity.lastModificationTimestamp
                .convertTimestamp()
                .toLastModificationTimestamp(),
            creationTimestamp = dexieNoteEntity.creationTimestamp
                .convertTimestamp()
                .toCreationTimestamp(),
            hasSyncFailed = dexieNoteEntity.hasSyncFailed,
            wasDeleted = dexieNoteEntity.wasDeleted
        )
    }

    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        val note = DexieNoteEntity(
            title = addNotePayload.title,
            content = addNotePayload.content,
            lastModificationTimestamp = addNotePayload.lastModificationTimestamp.unix.convertTimestamp(),
            creationTimestamp = addNotePayload.creationTimestamp.unix.convertTimestamp()
        )

        deleteObjectProperty(note, "localId")
        console.log(note)
        return DexieDatabase.noteTable.add(note).then {
            console.log(it)
            it
        }.await()
    }

    override suspend fun updateNote(updateNotePayload: UpdateNotePayload) {
        DexieDatabase.noteTable
            .where("creationTimestamp")
            .equals(updateNotePayload.creationTimestamp.unix.convertTimestamp())
            .modify(
                json(
                    "title" to updateNotePayload.title,
                    "content" to updateNotePayload.content,
                    "lastModificationTimestamp" to updateNotePayload.lastModificationTimestamp.unix.convertTimestamp()
                )
            )
    }

    override suspend fun updateSyncFailed(creationTimestamp: CreationTimestamp, hasSyncFailed: Boolean) {
        DexieDatabase.noteTable
            .where("creationTimestamp")
            .equals(creationTimestamp.unix.convertTimestamp())
            .modify(json("hasSyncFailed" to hasSyncFailed))
    }

    override suspend fun deleteNotes(creationTimestamps: List<CreationTimestamp>) {
        val stringTimestamps = creationTimestamps.map { it.unix.convertTimestamp() }
        DexieDatabase.noteTable
            .where("creationTimestamp")
            .anyOf(stringTimestamps.toTypedArray())
            .delete().then {
                console.log(it)
            }
    }

    override suspend fun setWasDeleted(
        creationTimestamps: List<CreationTimestamp>,
        wasDeleted: Boolean,
        lastModificationTimestamp: Long
    ) {
        val stringTimestamps = creationTimestamps.map { it.unix.convertTimestamp() }
        DexieDatabase.noteTable
            .where("creationTimestamp")
            .anyOf(stringTimestamps.toTypedArray())
            .modify(
                json(
                    "wasDeleted" to wasDeleted,
                    "lastModificationTimestamp" to lastModificationTimestamp.convertTimestamp()
                )
            )
    }

    private fun Long.convertTimestamp(): String = this.toString()

    private fun String.convertTimestamp(): Long = this.toLong()

    private fun deleteObjectProperty(jsObject: dynamic, key: String) = delete(jsObject[key])
}
