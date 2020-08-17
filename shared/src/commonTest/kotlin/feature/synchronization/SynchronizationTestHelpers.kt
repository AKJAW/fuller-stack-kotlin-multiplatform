package feature.synchronization

import com.soywiz.klock.DateTime
import database.NoteDao
import database.NoteEntity
import feature.SynchronizeNotes
import kotlinx.coroutines.CoroutineDispatcher
import model.Note
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import network.NoteApi
import network.NoteSchema

internal class SynchronizationUseCaseFactory(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {
    fun createSynchronizeNotes(): SynchronizeNotes = SynchronizeNotes(
        coroutineDispatcher = coroutineDispatcher,
        noteDao = noteDao,
        noteApi = noteApi,
        synchronizeDeletedNotes = createSynchronizeDeletedNotes(),
        synchronizeAddedNotes = createSynchronizeAddedNotes(),
        synchronizeUpdatedNotes = createSynchronizeUpdatedNotes()
    )

    fun createSynchronizeDeletedNotes(): SynchronizeDeletedNotes = SynchronizeDeletedNotes(
        coroutineDispatcher = coroutineDispatcher,
        noteDao = noteDao,
        noteApi = noteApi
    )

    fun createSynchronizeAddedNotes(): SynchronizeAddedNotes = SynchronizeAddedNotes(
        coroutineDispatcher = coroutineDispatcher,
        noteDao = noteDao,
        noteApi = noteApi
    )

    fun createSynchronizeUpdatedNotes(): SynchronizeUpdatedNotes = SynchronizeUpdatedNotes(
        coroutineDispatcher = coroutineDispatcher,
        noteDao = noteDao,
        noteApi = noteApi
    )
}

internal object SynchronizationTestData {
    val FIRST_NOTE_DATE = DateTime.createAdjusted(2020, 8, 3)
    val FIRST_NOTE = Note(
        title = "first",
        content = "first",
        lastModificationTimestamp = FIRST_NOTE_DATE.unixMillisLong.toLastModificationTimestamp(),
        creationTimestamp = FIRST_NOTE_DATE.unixMillisLong.toCreationTimestamp()
    )
    val SECOND_NOTE_DATE = DateTime.createAdjusted(2020, 8, 4)
    val SECOND_NOTE = Note(
        title = "second",
        content = "second",
        lastModificationTimestamp = SECOND_NOTE_DATE.unixMillisLong.toLastModificationTimestamp(),
        creationTimestamp = SECOND_NOTE_DATE.unixMillisLong.toCreationTimestamp()
    )
}

fun Note.copyToEntity(
    title: String? = null,
    content: String? = null,
    lastModificationTimestamp: Long? = null,
    creationTimestamp: Long? = null,
    hasSyncFailed: Boolean = false,
    wasDeleted: Boolean = false
) = NoteEntity(
    localId = -1,
    title = title ?: this.title,
    content = content ?: this.content,
    lastModificationTimestamp = lastModificationTimestamp?.toLastModificationTimestamp() ?: this.lastModificationTimestamp,
    creationTimestamp = creationTimestamp?.toCreationTimestamp() ?: this.creationTimestamp,
    hasSyncFailed = hasSyncFailed,
    wasDeleted = wasDeleted
)

fun Note.copyToSchema(
    title: String? = null,
    content: String? = null,
    lastModificationTimestamp: Long? = null,
    creationTimestamp: Long? = null
) = NoteSchema(
    apiId = -1,
    title = title ?: this.title,
    content = content ?: this.content,
    lastModificationTimestamp = lastModificationTimestamp?.toLastModificationTimestamp() ?: this.lastModificationTimestamp,
    creationTimestamp = creationTimestamp?.toCreationTimestamp() ?: this.creationTimestamp
)
