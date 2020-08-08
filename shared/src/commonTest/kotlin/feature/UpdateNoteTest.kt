package feature

import base.CommonDispatchers
import com.soywiz.klock.DateTime
import database.NoteEntity
import helpers.date.UnixTimestampProviderFake
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.Note
import model.NoteIdentifier
import network.NoteSchema
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UpdateNoteTest {

    companion object {
        private val date = DateTime.createAdjusted(2020, 7, 28)
        private val INITIAL_NOTE = Note(
            noteIdentifier = NoteIdentifier(1),
            title = "title",
            content = "content",
            lastModificationTimestamp = LastModificationTimestamp(date.unixMillisLong),
            creationTimestamp = CreationTimestamp(date.unixMillisLong)
        )
        private const val UPDATED_TITLE = "Updated title"
        private const val UPDATED_CONTENT = "Updated content"
    }

    private lateinit var unixTimestampProviderFake: UnixTimestampProviderFake
    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: UpdateNote

    @BeforeTest
    fun setUp() {
        unixTimestampProviderFake = UnixTimestampProviderFake()
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = UpdateNote(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            unixTimestampProvider = unixTimestampProviderFake,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        noteDaoTestFake.initializeNoteEntities(listOf(INITIAL_NOTE))
        noteApiTestFake.initializeSchemas(listOf(INITIAL_NOTE))

    }

    @JsName("TrueReturnedOnApiSuccess")
    @Test
    fun `When the API call is successful then return true`() = runTest {
        val result = SUT.executeAsync(INITIAL_NOTE.noteIdentifier, UPDATED_TITLE, UPDATED_CONTENT)

        assertTrue(result)
    }

    @JsName("FalseReturnedOnApiFail")
    @Test
    fun `When the API call fails then return false`() = runTest {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(INITIAL_NOTE.noteIdentifier, UPDATED_TITLE, UPDATED_CONTENT)

        assertFalse(result)
    }

    @JsName("NoteUpdatedInLocalDatabase")
    @Test
    fun `Note is updated in the local database`() = runTest {
        val lastModificationTimestamp = 50L
        unixTimestampProviderFake.timestamp = lastModificationTimestamp

        SUT.executeAsync(INITIAL_NOTE.noteIdentifier, UPDATED_TITLE, UPDATED_CONTENT)

        val expectedNote = NoteEntity(
            id = INITIAL_NOTE.noteIdentifier.id,
            noteId = INITIAL_NOTE.noteIdentifier.id,
            title = UPDATED_TITLE,
            content = UPDATED_CONTENT,
            lastModificationTimestamp = LastModificationTimestamp(lastModificationTimestamp),
            creationTimestamp = INITIAL_NOTE.creationTimestamp
        )
        assertEquals(expectedNote, noteDaoTestFake.notes.first())
    }

    @JsName("NoteUpdatedInAPI")
    @Test
    fun `Note is updated in the API`() = runTest {
        val lastModificationTimestamp = 50L
        unixTimestampProviderFake.timestamp = lastModificationTimestamp

        SUT.executeAsync(INITIAL_NOTE.noteIdentifier, UPDATED_TITLE, UPDATED_CONTENT)

        val expectedNote = NoteSchema(
            apiId = INITIAL_NOTE.noteIdentifier.id,
            title = UPDATED_TITLE,
            content = UPDATED_CONTENT,
            lastModificationTimestamp = LastModificationTimestamp(lastModificationTimestamp),
            creationTimestamp = INITIAL_NOTE.creationTimestamp
        )
        assertEquals(expectedNote, noteApiTestFake.notes.first())
    }

    @JsName("SyncFailedSetOnApiFail")
    @Test
    fun `When API request fails then sync failed is set`() = runTest {
        noteApiTestFake.willFail = true

        SUT.executeAsync(INITIAL_NOTE.noteIdentifier, UPDATED_TITLE, UPDATED_CONTENT)

        assertTrue(noteDaoTestFake.notes.first().hasSyncFailed)
    }

}
