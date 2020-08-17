package feature

import base.CommonDispatchers
import database.NoteEntity
import helpers.date.UnixTimestampProviderFake
import model.toCreationTimestamp
import model.toLastModificationTimestamp
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

class AddNoteTest {

    companion object {
        private const val TIMESTAMP = 70L
        private const val TITLE = "title"
        private const val CONTENT = "content"
    }

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var unixTimestampProviderFake: UnixTimestampProviderFake
    private lateinit var SUT: AddNote

    @BeforeTest
    fun setUp() {
        unixTimestampProviderFake = UnixTimestampProviderFake()
        unixTimestampProviderFake.timestamp = TIMESTAMP
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = AddNote(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake,
            unixTimestampProvider = unixTimestampProviderFake
        )
    }

    @JsName("TrueReturnedOnApiSuccess")
    @Test
    fun `When the API call is successful then return true`() = runTest {

        val result = SUT.executeAsync(TITLE, CONTENT)

        assertTrue(result)
    }

    @JsName("FalseReturnedOnApiFail")
    @Test
    fun `When the API call fails then return false`() = runTest {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(TITLE, CONTENT)

        assertFalse(result)
    }

    @JsName("AddsTheNoteToTheLocalDatabase")
    @Test
    fun `Adds the note to the local database`() = runTest {

        SUT.executeAsync(TITLE, CONTENT)

        val expectedNote = NoteEntity(
            localId = 0,
            title = TITLE,
            content = CONTENT,
            lastModificationTimestamp = TIMESTAMP.toLastModificationTimestamp(),
            creationTimestamp = TIMESTAMP.toCreationTimestamp()
        )
        assertEquals(expectedNote, noteDaoTestFake.notes.first())
    }

    @JsName("AddsTheNoteToTheAPI")
    @Test
    fun `Adds the note to the API`() = runTest {

        SUT.executeAsync(TITLE, CONTENT)

        val expectedNote = NoteSchema(
            apiId = 0,
            title = TITLE,
            content = CONTENT,
            lastModificationTimestamp = TIMESTAMP.toLastModificationTimestamp(),
            creationTimestamp = TIMESTAMP.toCreationTimestamp()
        )
        assertEquals(expectedNote, noteApiTestFake.notes.first())
    }

    @JsName("SyncFailedIsSet")
    @Test
    fun `When request fails then set sync failed in the local database`() = runTest {
        noteApiTestFake.willFail = true

        SUT.executeAsync(TITLE, CONTENT)

        assertEquals(true, noteDaoTestFake.notes.first().hasSyncFailed)
    }

}
