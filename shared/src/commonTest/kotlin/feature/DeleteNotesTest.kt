package feature

import base.CommonDispatchers
import model.Note
import model.NoteIdentifier
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
        private val FIRST_NOTE = Note(noteIdentifier = NoteIdentifier(1), title = "first", content = "first")
        private val SECOND_NOTE = Note(noteIdentifier = NoteIdentifier(2), title = "second", content = "second")
    }

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: DeleteNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = DeleteNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        noteDaoTestFake.initializeNoteEntities(listOf(FIRST_NOTE, SECOND_NOTE))
        noteApiTestFake.initializeSchemas(listOf(FIRST_NOTE, SECOND_NOTE))
    }

    @JsName("TrueReturnedOnApiSuccess")
    @Test
    fun `When the API call is successful then return true`() = runTest {
        val result = SUT.executeAsync(listOf(FIRST_NOTE.noteIdentifier, SECOND_NOTE.noteIdentifier))

        assertTrue(result)
    }

    @JsName("FalseReturnedOnApiFail")
    @Test
    fun `When the API call fails then return false`() = runTest {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(listOf(FIRST_NOTE.noteIdentifier, SECOND_NOTE.noteIdentifier))

        assertFalse(result)
    }

    @JsName("wasDeletedSetToTrue")
    @Test
    fun `When the API call fails then the entities remain in the local database with wasDeleted set to true`() = runTest {
        noteApiTestFake.willFail = true

        SUT.executeAsync(listOf(FIRST_NOTE.noteIdentifier, SECOND_NOTE.noteIdentifier))

        val wereAllDeleted = noteDaoTestFake.notes.all { it.wasDeleted }
        assertTrue(wereAllDeleted)
        assertEquals(2, noteDaoTestFake.notes.count())
    }

    @JsName("NotesAreDeletedFromLocalDatabaseWhenApiSucceeds")
    @Test
    fun `Notes are deleted from the local database when API call succeeds`() = runTest {

        SUT.executeAsync(listOf(FIRST_NOTE.noteIdentifier, SECOND_NOTE.noteIdentifier))

        assertEquals(0, noteDaoTestFake.notes.count())
    }

    @JsName("NotesAreDeletedFromAPI")
    @Test
    fun `Notes are deleted from the API`() = runTest {

        SUT.executeAsync(listOf(FIRST_NOTE.noteIdentifier, SECOND_NOTE.noteIdentifier))

        assertEquals(0, noteApiTestFake.notes.count())
    }
}
