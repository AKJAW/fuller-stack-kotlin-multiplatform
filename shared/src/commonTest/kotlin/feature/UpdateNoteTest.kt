package feature

import base.CommonDispatchers
import com.soywiz.klock.DateTime
import database.NoteEntity
import helpers.date.TimestampProviderFake
import model.Note
import model.NoteIdentifier
import model.schema.NoteSchema
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
            lastModificationDate = date,
            creationDate = date
        )
        private val INITIAL_ENTITY = NoteEntity(
            id = 0,
            noteId = INITIAL_NOTE.noteIdentifier.id,
            title = INITIAL_NOTE.title,
            content = INITIAL_NOTE.content,
            lastModificationTimestamp = INITIAL_NOTE.lastModificationDate.unixMillisLong,
            creationTimestamp = INITIAL_NOTE.creationDate.unixMillisLong,
            hasSyncFailed = false
        )
        private val INITIAL_SCHEMA = NoteSchema(
            apiId = INITIAL_NOTE.noteIdentifier.id,
            title = INITIAL_NOTE.title,
            content = INITIAL_NOTE.content,
            lastModificationTimestamp = INITIAL_NOTE.lastModificationDate.unixMillisLong,
            creationTimestamp = INITIAL_NOTE.creationDate.unixMillisLong
        )
        private const val UPDATED_TITLE = "Updated title"
        private const val UPDATED_CONTENT = "Updated content"
    }

    private lateinit var timestampProvider: TimestampProviderFake
    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: NewUpdateNote

    @BeforeTest
    fun setUp() {
        timestampProvider = TimestampProviderFake()
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = NewUpdateNote(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            timestampProvider = timestampProvider,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        noteDaoTestFake.notes = mutableListOf(INITIAL_ENTITY)
        noteApiTestFake.notes = mutableListOf(INITIAL_SCHEMA)

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
        timestampProvider.timestamp = lastModificationTimestamp

        SUT.executeAsync(INITIAL_NOTE.noteIdentifier, UPDATED_TITLE, UPDATED_CONTENT)

        val expectedNote = INITIAL_ENTITY
            .copy(
                title = UPDATED_TITLE,
                content = UPDATED_CONTENT,
                lastModificationTimestamp = lastModificationTimestamp
            )
        assertEquals(expectedNote, noteDaoTestFake.notes.first())
    }

    @JsName("NoteUpdatedInAPI")
    @Test
    fun `Note is updated in the API`() = runTest {
        val lastModificationTimestamp = 50L
        timestampProvider.timestamp = lastModificationTimestamp

        SUT.executeAsync(INITIAL_NOTE.noteIdentifier, UPDATED_TITLE, UPDATED_CONTENT)

        val expectedNote = INITIAL_SCHEMA
            .copy(
                title = UPDATED_TITLE,
                content = UPDATED_CONTENT,
                lastModificationTimestamp = lastModificationTimestamp
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
