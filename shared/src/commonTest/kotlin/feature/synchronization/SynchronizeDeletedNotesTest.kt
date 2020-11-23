package feature.synchronization

import base.CommonDispatchers
import com.soywiz.klock.days
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.FIRST_NOTE_DATE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE_DATE
import helpers.date.UnixTimestampProviderFake
import io.kotest.core.spec.style.FunSpec
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.test.assertEquals

class SynchronizeDeletedNotesTest : FunSpec({

    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    val timestampProviderFake = UnixTimestampProviderFake()
    lateinit var SUT: SynchronizeDeletedNotes

    beforeTest {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = SynchronizeDeletedNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            timestampProvider = timestampProviderFake,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
    }

    suspendingTest("Local wasDeleted true and last modification date the same then delete notes from Local and API") {
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

    suspendingTest("Local wasDeleted true and last modification date is older then dont delete notes") {
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

    suspendingTest("Local wasDeleted true and last modification date is older then revert wasDeleted") {
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

    suspendingTest("Local wasDeleted true and last modification date is older then set last modification as in the API") {
        val firstNewerModificationTimestamp = FIRST_NOTE_DATE.plus(1.days).unixMillisLong
        val secondNewerModificationTimestamp = SECOND_NOTE_DATE.plus(5.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(wasDeleted = true),
            SECOND_NOTE.copyToEntity(wasDeleted = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(lastModificationTimestamp = firstNewerModificationTimestamp),
            SECOND_NOTE.copyToSchema(lastModificationTimestamp = secondNewerModificationTimestamp)
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(firstNewerModificationTimestamp, noteDaoTestFake.notes[0].lastModificationTimestamp.unix)
        assertEquals(secondNewerModificationTimestamp, noteDaoTestFake.notes[1].lastModificationTimestamp.unix)
    }

    suspendingTest("Api wasDeleted true and last modification date is the same then delete notes") {
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

    suspendingTest("Api wasDeleted true and last modification date is older then revert wasDeleted") {
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

    suspendingTest("Api wasDeleted true and last modification date is older then update last modification") {
        val firstNewerModificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        val secondNewerModificationTimestamp = SECOND_NOTE_DATE.plus(5.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(lastModificationTimestamp = firstNewerModificationTimestamp),
            SECOND_NOTE.copyToEntity(lastModificationTimestamp = secondNewerModificationTimestamp)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(wasDeleted = true),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(firstNewerModificationTimestamp, noteApiTestFake.notes[0].lastModificationTimestamp.unix)
        assertEquals(secondNewerModificationTimestamp, noteApiTestFake.notes[1].lastModificationTimestamp.unix)
    }
})
