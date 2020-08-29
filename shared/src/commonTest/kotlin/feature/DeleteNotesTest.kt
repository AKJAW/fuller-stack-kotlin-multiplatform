package feature

import base.CommonDispatchers
import helpers.date.UnixTimestampProviderFake
import model.Note
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DeleteNotesTest {

    companion object {
        private val FIRST_NOTE = Note(
            title = "first",
            content = "first",
            lastModificationTimestamp = 1L.toLastModificationTimestamp(),
            creationTimestamp = 1L.toCreationTimestamp()
        )
        private val SECOND_NOTE = Note(
            title = "second",
            content = "second",
            lastModificationTimestamp = 2L.toLastModificationTimestamp(),
            creationTimestamp = 2L.toCreationTimestamp()
        )
    }

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private val timestampProviderFake = UnixTimestampProviderFake()
    private lateinit var SUT: DeleteNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = DeleteNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            timestampProvider = timestampProviderFake,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        noteDaoTestFake.initializeNoteEntities(listOf(FIRST_NOTE, SECOND_NOTE))
        noteApiTestFake.initializeSchemas(listOf(FIRST_NOTE, SECOND_NOTE))
    }

    @JsName("TrueReturnedOnApiSuccess")
    @Test
    fun `When the API call is successful then return true`() = runTest {
        val result = SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        assertTrue(result)
    }

    @JsName("FalseReturnedOnApiFail")
    @Test
    fun `When the API call fails then return false`() = runTest {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        assertFalse(result)
    }

    @JsName("wasDeletedSetToTrue")
    @Test
    fun `When the API call fails then the entities remain in the local database with wasDeleted set to true`() = runTest {
        noteApiTestFake.willFail = true

        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        val wereAllDeleted = noteDaoTestFake.notes.all { it.wasDeleted }
        assertTrue(wereAllDeleted)
        assertEquals(2, noteDaoTestFake.notes.count())
    }

    @JsName("NotesAreDeletedFromLocalDatabaseWhenApiSucceeds")
    @Test
    fun `Notes are deleted from the local database when API call succeeds`() = runTest {

        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        assertEquals(0, noteDaoTestFake.notes.count())
    }

    @JsName("NotesAreDeletedFromAPI")
    @Test
    fun `Notes are deleted from the API`() = runTest {

        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        assertEquals(0, noteApiTestFake.notes.filterNot { it.wasDeleted }.count())
    }

    @JsName("localLastModificationTimestampUpdated")
    @Test
    fun `When the API call fails then the entities remain in the local database with an updated lastModificationTimestamp`() = runTest {
        noteApiTestFake.willFail = true
        timestampProviderFake.timestamp = 100L

        SUT.executeAsync(listOf(SECOND_NOTE.creationTimestamp))

        assertEquals(1L, noteDaoTestFake.notes[0].lastModificationTimestamp.unix)
        assertEquals(100L, noteDaoTestFake.notes[1].lastModificationTimestamp.unix)
    }
}
