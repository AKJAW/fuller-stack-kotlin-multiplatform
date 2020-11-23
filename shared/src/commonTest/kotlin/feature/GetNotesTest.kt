package feature

import base.CommonDispatchers
import database.NoteEntityMapper
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.flow.first
import model.Note
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.test.assertEquals

class GetNotesTest : FunSpec({

    val FIRST_NOTE = Note(
        title = "first",
        content = "first",
        lastModificationTimestamp = 1L.toLastModificationTimestamp(),
        creationTimestamp = 1L.toCreationTimestamp()
    )
    val SECOND_NOTE = Note(
        title = "second",
        content = "second",
        lastModificationTimestamp = 2L.toLastModificationTimestamp(),
        creationTimestamp = 2L.toCreationTimestamp()
    )

    val noteEntityMapper: NoteEntityMapper = NoteEntityMapper()
    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    lateinit var SUT: GetNotes

    beforeTest {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = GetNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteEntityMapper = noteEntityMapper
        )
    }

    suspendingTest("Returns the notes from the database") {
        noteDaoTestFake.initializeNoteEntities(listOf(FIRST_NOTE, SECOND_NOTE))

        val flow = SUT.executeAsync()

        val flowNotes = flow.first()
        assertEquals(2, flowNotes.count())
    }

    suspendingTest("Deleted notes are not returned") {
        noteDaoTestFake.initializeNoteEntities(listOf(FIRST_NOTE, SECOND_NOTE))
        noteDaoTestFake.setWasDeleted(listOf(SECOND_NOTE.creationTimestamp), true, 0L)

        val flow = SUT.executeAsync()

        val flowNotes = flow.first()
        assertEquals(1, flowNotes.count())
        assertEquals(FIRST_NOTE, flowNotes.first())
    }
})
