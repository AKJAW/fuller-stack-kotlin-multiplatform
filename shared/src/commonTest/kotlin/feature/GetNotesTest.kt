package feature

import base.CommonDispatchers
import database.NoteEntityMapper
import kotlinx.coroutines.flow.first
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.Note
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetNotesTest {

    companion object {
        private val FIRST_NOTE = Note(
            title = "first",
            content = "first",
            lastModificationTimestamp = LastModificationTimestamp(1),
            creationTimestamp = CreationTimestamp(1)
        )
        private val SECOND_NOTE = Note(
            title = "second",
            content = "second",
            lastModificationTimestamp = LastModificationTimestamp(2),
            creationTimestamp = CreationTimestamp(2)
        )
    }

    private val noteEntityMapper: NoteEntityMapper = NoteEntityMapper()
    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: GetNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = GetNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteEntityMapper = noteEntityMapper
        )
    }

    @JsName("ReturnsNotesFromDatabase")
    @Test
    fun `Returns the notes from the database`() = runTest {
        noteDaoTestFake.initializeNoteEntities(listOf(FIRST_NOTE, SECOND_NOTE))

        val flow = SUT.executeAsync()

        val flowNotes = flow.first()
        assertEquals(2, flowNotes.count())
    }

    @JsName("DeletedNotesNotReturned")
    @Test
    fun `Deleted notes are not returned`() = runTest {
        noteDaoTestFake.initializeNoteEntities(listOf(FIRST_NOTE, SECOND_NOTE))
        noteDaoTestFake.setWasDeleted(listOf(SECOND_NOTE.creationTimestamp), true)

        val flow = SUT.executeAsync()

        val flowNotes = flow.first()
        assertEquals(1, flowNotes.count())
        assertEquals(FIRST_NOTE, flowNotes.first())
    }
}
