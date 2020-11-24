package feature.synchronization

import base.CommonDispatchers
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import helpers.date.UnixTimestampProviderFake
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldHaveSize
import suspendingTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake

class SynchronizeNotesTest : FunSpec({

    lateinit var noteDaoTestFake: NoteDaoTestFake
    lateinit var noteApiTestFake: NoteApiTestFake
    val timestampProviderFake = UnixTimestampProviderFake()
    lateinit var synchronizationUseCaseFactory: SynchronizationUseCaseFactory
    lateinit var SUT: SynchronizeNotes

    beforeTest {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        synchronizationUseCaseFactory = SynchronizationUseCaseFactory(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake,
            timestampProvider = timestampProviderFake
        )
        SUT = synchronizationUseCaseFactory.createSynchronizeNotes()
    }

    suspendingTest("Deleted api notes are not added to the local database") {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        noteDaoTestFake.notes shouldHaveSize 1
    }

    suspendingTest("Deleted api notes are not added to the api") {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        noteApiTestFake.notes shouldHaveSize 2
    }

    suspendingTest("Deleted api notes are not updated in the local database") {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(title = "new")
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        noteDaoTestFake.notes shouldHaveSize 1
    }

    suspendingTest("Deleted api notes which were deleted locally are not re-added") {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        noteDaoTestFake.notes shouldHaveSize 1
    }
})
