package feature.socket

import base.CommonDispatchers
import feature.synchronization.SynchronizationTestData
import feature.synchronization.SynchronizeNotesMock
import feature.synchronization.copyToEntity
import feature.synchronization.copyToSchema
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import suspendingTest
import tests.NoteDaoTestFake
import tests.NoteSocketFake

class ListenToSocketUpdatesTest : FunSpec({

    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteSocketFake: NoteSocketFake
    val synchronizeNotesMock = SynchronizeNotesMock()
    lateinit var SUT: ListenToSocketUpdates

    beforeTest {
        noteDaoTestFake = NoteDaoTestFake()
        noteSocketFake = NoteSocketFake()
        SUT = ListenToSocketUpdates(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteSocket = noteSocketFake,
            synchronizeNotes = synchronizeNotesMock
        )
    }

    suspendingTest("When the socket sends new data, it is synchronized") {
        val localNotes = listOf(
            SynchronizationTestData.FIRST_NOTE.copyToEntity(),
            SynchronizationTestData.SECOND_NOTE.copyToEntity()
        )
        noteDaoTestFake.notes = localNotes
        SUT.listenToSocketChanges()
        val apiNotes = listOf(
            SynchronizationTestData.FIRST_NOTE.copyToSchema(),
            SynchronizationTestData.SECOND_NOTE.copyToSchema()
        )

        noteSocketFake.notes = apiNotes

        delay(10) //cough cough
        assertSoftly {
            synchronizeNotesMock.executeAsyncWithParamsCalled.shouldBeTrue()
            synchronizeNotesMock.passedInLocalNotes shouldBe localNotes
            synchronizeNotesMock.passedInApiNotes shouldBe apiNotes
        }
    }
})
