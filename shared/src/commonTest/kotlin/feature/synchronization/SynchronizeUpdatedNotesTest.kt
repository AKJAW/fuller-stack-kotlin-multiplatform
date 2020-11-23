package feature.synchronization

import base.CommonDispatchers
import com.soywiz.klock.days
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import io.kotest.core.spec.style.FunSpec
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.test.assertEquals

class SynchronizeUpdatedNotesTest : FunSpec({

    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    lateinit var SUT: SynchronizeUpdatedNotes

    beforeTest {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = SynchronizeUpdatedNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
    }

    suspendingTest("Local is more recent then update the API") {
        val modificationTimestamp = SynchronizationTestData.SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(title = "changed", lastModificationTimestamp = modificationTimestamp)
        )
        noteApiTestFake.notes = mutableListOf(FIRST_NOTE.copyToSchema(), SECOND_NOTE.copyToSchema())

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(FIRST_NOTE.title, noteApiTestFake.notes[0].title)
        assertEquals("changed", noteApiTestFake.notes[1].title)
    }

    suspendingTest("After note is updated in the API hasSyncFailed is updated") {
        val modificationTimestamp = SynchronizationTestData.SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(
                title = "changed",
                lastModificationTimestamp = modificationTimestamp,
                hasSyncFailed = true
            )
        )
        noteApiTestFake.notes = mutableListOf(FIRST_NOTE.copyToSchema(), SECOND_NOTE.copyToSchema())

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(false, noteDaoTestFake.notes[1].hasSyncFailed)
    }

    suspendingTest("API is more recent then update local database") {
        val modificationTimestamp = SynchronizationTestData.SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(FIRST_NOTE.copyToEntity(), SECOND_NOTE.copyToEntity())
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(title = "changed api", lastModificationTimestamp = modificationTimestamp)
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(FIRST_NOTE.title, noteDaoTestFake.notes[0].title)
        assertEquals("changed api", noteDaoTestFake.notes[1].title)
    }
})
