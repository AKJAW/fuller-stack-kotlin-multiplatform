package feature

import base.CommonDispatchers
import com.soywiz.klock.DateTime
import database.NoteEntityMapper
import kotlinx.coroutines.flow.first
import model.Note
import model.NoteIdentifier
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetNotesTest {

    companion object {
        private val date = DateTime.createAdjusted(2020, 8, 1)
        private val FIRST_NOTE = Note(
            NoteIdentifier(1),
            title = "first",
            content = "first"
        )
        private val SECOND_NOTE = Note(
            NoteIdentifier(2),
            title = "second",
            content = "second"
        )
        private val DELETED_NOTE = Note(
            NoteIdentifier(3),
            title = "second",
            content = "second"
        )
    }

    private val noteEntityMapper: NoteEntityMapper = NoteEntityMapper()
    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: NewGetNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = NewGetNotes(
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
        noteDaoTestFake.setWasDeleted(listOf(SECOND_NOTE.noteIdentifier.id))

        val flow = SUT.executeAsync()

        val flowNotes = flow.first()
        assertEquals(1, flowNotes.count())
        assertEquals(FIRST_NOTE, flowNotes.first())
    }

    //If API notes are different, then SyncUseCase is called?
    //If any notes have syncFailed then API
    //If any notes have wasDeleted then API
    //When API fails then do something?


}
