package feature

import base.CommonDispatchers
import database.NoteEntity
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

class DeleteNotesTest {

    companion object {
        private const val FIRST_ID = 1
        private val FIRST_NOTE = Note(noteIdentifier = NoteIdentifier(FIRST_ID), title = "first", content = "first")
        private const val SECOND_ID = 2
        private val SECOND_NOTE = Note(noteIdentifier = NoteIdentifier(SECOND_ID), title = "second", content = "second")

        private val ENTITIES = listOf(
            createEntity(FIRST_NOTE),
            createEntity(SECOND_NOTE)
        )

        private val SCHEMAS = listOf(
            NoteSchema(apiId = FIRST_ID, title = FIRST_NOTE.title, content = FIRST_NOTE.content),
            NoteSchema(apiId = SECOND_ID, title = SECOND_NOTE.title, content = SECOND_NOTE.content)
        )

        private fun createEntity(note: Note): NoteEntity {
            val timestamp = 0L
            return NoteEntity(
                id = note.noteIdentifier.id,
                noteId = note.noteIdentifier.id,
                title = note.title,
                content = note.content,
                lastModificationTimestamp = timestamp,
                creationTimestamp = timestamp
            )
        }
    }

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: NewDeleteNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = NewDeleteNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        noteDaoTestFake.notes = ENTITIES.toMutableList()
        noteApiTestFake.notes = SCHEMAS.toMutableList()
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
    fun `the wasDeleted property is set to true if notes cant be deleted`() = runTest {
        noteApiTestFake.willFail = true

        SUT.executeAsync(listOf(FIRST_NOTE.noteIdentifier, SECOND_NOTE.noteIdentifier))

        val wereAllDeleted = noteDaoTestFake.notes.all { it.wasDeleted }
        assertTrue(wereAllDeleted)
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

    @JsName("NotesAreNotDeletedFromDatabaseIfApiFailed")
    @Test
    fun `Notes are not deleted from the local database if API call failed`() = runTest {
        noteApiTestFake.willFail = true

        SUT.executeAsync(listOf(FIRST_NOTE.noteIdentifier, SECOND_NOTE.noteIdentifier))

        assertEquals(2, noteDaoTestFake.notes.count())
    }

}
