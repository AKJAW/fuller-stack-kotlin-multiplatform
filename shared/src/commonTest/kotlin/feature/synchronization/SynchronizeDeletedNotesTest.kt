package feature.synchronization

import base.CommonDispatchers
import com.soywiz.klock.days
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE_DATE
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SynchronizeDeletedNotesTest {

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: SynchronizeDeletedNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = SynchronizeDeletedNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
    }

    @JsName("LocalWasDeletedAndRecentThenDeleteFromApi")
    @Test
    fun `Local wasDeleted true and last modification date the same then delete notes from Local and API`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(wasDeleted = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema()
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(1, noteDaoTestFake.notes.count())
        assertEquals(1, noteApiTestFake.notes.filterNot { it.wasDeleted }.count())
    }

    @JsName("LocalWasDeletedAndOlderThenDontDelete")
    @Test
    fun `Local wasDeleted true and last modification date is older then dont delete notes`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(wasDeleted = true)
        )
        val newerModificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(lastModificationTimestamp = newerModificationTimestamp)
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(2, noteDaoTestFake.notes.count())
        assertEquals(2, noteApiTestFake.notes.count())
    }

    @JsName("LocalWasDeletedAndOlderThenRevertLocal")
    @Test
    fun `Local wasDeleted true and last modification date is older then revert wasDeleted`() = runTest {
        val newerModificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(wasDeleted = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(lastModificationTimestamp = newerModificationTimestamp)
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(false, noteDaoTestFake.notes[1].wasDeleted)
    }

    @JsName("ApiWasDeletedAndRecentThenDeleteLocal")
    @Test
    fun `Api wasDeleted true and last modification date is the same then delete notes`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        println(noteDaoTestFake.notes)
        assertEquals(1, noteDaoTestFake.notes.count())
        assertEquals(1, noteApiTestFake.notes.filterNot { it.wasDeleted }.count())
    }

    @JsName("ApiWasDeletedAndOlderThenRevertApi")
    @Test
    fun `Api wasDeleted true and last modification date is older then revert wasDeleted`() = runTest {
        val newerModificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(lastModificationTimestamp = newerModificationTimestamp)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(false, noteApiTestFake.notes[1].wasDeleted)
    }
}