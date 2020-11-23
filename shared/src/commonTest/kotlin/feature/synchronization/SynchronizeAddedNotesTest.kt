package feature.synchronization

import base.CommonDispatchers
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import io.kotest.core.spec.style.FunSpec
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.test.assertEquals

class SynchronizeAddedNotesTest : FunSpec({

    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    lateinit var SUT: SynchronizeAddedNotes

    beforeTest {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = SynchronizeAddedNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
    }

    suspendingTest("When local database has new notes the add them to the api") {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema()
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(2, noteApiTestFake.notes.count())
        val addedNote = noteApiTestFake.notes[1]
        assertEquals(SECOND_NOTE.title, addedNote.title)
        assertEquals(SECOND_NOTE.content, addedNote.content)
        assertEquals(SECOND_NOTE.creationTimestamp, addedNote.creationTimestamp)
        assertEquals(SECOND_NOTE.lastModificationTimestamp, addedNote.lastModificationTimestamp)
    }

    suspendingTest("When local database has new notes after adding them to API hasSyncFailed is set to false") {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(hasSyncFailed = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema()
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(false, noteDaoTestFake.notes[1].hasSyncFailed)
    }

    suspendingTest("When API has new notes the add them locally") {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema()
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)
        assertEquals(2, noteDaoTestFake.notes.count())
        val addedNote = noteDaoTestFake.notes[1]
        assertEquals(SECOND_NOTE.title, addedNote.title)
        assertEquals(SECOND_NOTE.content, addedNote.content)
        assertEquals(SECOND_NOTE.creationTimestamp, addedNote.creationTimestamp)
        assertEquals(SECOND_NOTE.lastModificationTimestamp, addedNote.lastModificationTimestamp)
    }
})
