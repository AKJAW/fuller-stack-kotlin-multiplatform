package feature

import base.CommonDispatchers
import helpers.date.UnixTimestampProviderFake
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import model.Note
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake

class DeleteNotesTest : FunSpec({

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

    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    val timestampProviderFake = UnixTimestampProviderFake()
    lateinit var SUT: DeleteNotes

    beforeTest {
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

    suspendingTest("When the API call is successful then return true") {
        val result = SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        result.shouldBeTrue()
    }

    suspendingTest("When the API call fails then return false") {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        result.shouldBeFalse()
    }

    suspendingTest("When the API call fails then the entities remain in the local database with wasDeleted set to true") {
        noteApiTestFake.willFail = true

        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        val wereAllDeleted = noteDaoTestFake.notes.all { it.wasDeleted }
        wereAllDeleted.shouldBeTrue()
        noteDaoTestFake.notes shouldHaveSize 2
    }

    suspendingTest("Notes are deleted from the local database when API call succeeds") {
        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        noteDaoTestFake.notes shouldHaveSize 0
    }

    suspendingTest("Notes are deleted from the API") {
        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        val remainingNotes = noteApiTestFake.notes.filterNot { it.wasDeleted }
        remainingNotes shouldHaveSize 0
    }

    suspendingTest("Deleted API notes have update lastModificationTimestamp") {
        timestampProviderFake.timestamp = 100L

        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        val deletedNotes = noteApiTestFake.notes.filter { it.wasDeleted }
        deletedNotes[0].lastModificationTimestamp.unix shouldBe 100L
        deletedNotes[1].lastModificationTimestamp.unix shouldBe 100L
    }

    suspendingTest("When the API call fails then the entities remain in the local database with an updated lastModificationTimestamp") {
        noteApiTestFake.willFail = true
        timestampProviderFake.timestamp = 100L

        SUT.executeAsync(listOf(SECOND_NOTE.creationTimestamp))

        noteDaoTestFake.notes[0].lastModificationTimestamp.unix shouldBe 1L
        noteDaoTestFake.notes[1].lastModificationTimestamp.unix shouldBe 100L
    }
})
