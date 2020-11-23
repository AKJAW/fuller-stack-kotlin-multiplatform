package feature

import base.CommonDispatchers
import helpers.date.UnixTimestampProviderFake
import io.kotest.core.spec.style.FunSpec
import model.Note
import model.toCreationTimestamp
import model.toLastModificationTimestamp
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

        assertTrue(result)
    }

    suspendingTest("When the API call fails then return false") {
        noteApiTestFake.willFail = true

        val result = SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        assertFalse(result)
    }

    suspendingTest("When the API call fails then the entities remain in the local database with wasDeleted set to true") {
        noteApiTestFake.willFail = true

        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        val wereAllDeleted = noteDaoTestFake.notes.all { it.wasDeleted }
        assertTrue(wereAllDeleted)
        assertEquals(2, noteDaoTestFake.notes.count())
    }

    suspendingTest("Notes are deleted from the local database when API call succeeds") {
        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        assertEquals(0, noteDaoTestFake.notes.count())
    }

    suspendingTest("Notes are deleted from the API") {
        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        assertEquals(0, noteApiTestFake.notes.filterNot { it.wasDeleted }.count())
    }

    suspendingTest("Deleted API notes have update lastModificationTimestamp") {
        timestampProviderFake.timestamp = 100L

        SUT.executeAsync(listOf(FIRST_NOTE.creationTimestamp, SECOND_NOTE.creationTimestamp))

        val deletedNotes = noteApiTestFake.notes.filter { it.wasDeleted }
        assertEquals(100L, deletedNotes[0].lastModificationTimestamp.unix)
        assertEquals(100L, deletedNotes[1].lastModificationTimestamp.unix)
    }

    suspendingTest("When the API call fails then the entities remain in the local database with an updated lastModificationTimestamp") {
        noteApiTestFake.willFail = true
        timestampProviderFake.timestamp = 100L

        SUT.executeAsync(listOf(SECOND_NOTE.creationTimestamp))

        assertEquals(1L, noteDaoTestFake.notes[0].lastModificationTimestamp.unix)
        assertEquals(100L, noteDaoTestFake.notes[1].lastModificationTimestamp.unix)
    }
})
