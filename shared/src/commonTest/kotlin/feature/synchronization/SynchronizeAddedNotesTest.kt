package feature.synchronization

import base.CommonDispatchers
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake

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

        assertSoftly {
            noteApiTestFake.notes shouldHaveSize 2
            val addedNote = noteApiTestFake.notes[1]
            addedNote.title shouldBe SECOND_NOTE.title
            addedNote.content shouldBe SECOND_NOTE.content
            addedNote.creationTimestamp shouldBe SECOND_NOTE.creationTimestamp
            addedNote.lastModificationTimestamp shouldBe SECOND_NOTE.lastModificationTimestamp
        }
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

        noteDaoTestFake.notes[1].hasSyncFailed.shouldBeFalse()
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

        assertSoftly {
            noteDaoTestFake.notes shouldHaveSize 2
            val addedNote = noteDaoTestFake.notes[1]
            addedNote.title shouldBe SECOND_NOTE.title
            addedNote.content shouldBe SECOND_NOTE.content
            addedNote.creationTimestamp shouldBe SECOND_NOTE.creationTimestamp
            addedNote.lastModificationTimestamp shouldBe SECOND_NOTE.lastModificationTimestamp
        }
    }
})
