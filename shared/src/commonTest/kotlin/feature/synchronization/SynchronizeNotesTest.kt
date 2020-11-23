package feature.synchronization

import base.CommonDispatchers
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import helpers.date.UnixTimestampProviderFake
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SynchronizeNotesTest {

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private val timestampProviderFake = UnixTimestampProviderFake()
    private lateinit var synchronizationUseCaseFactory: SynchronizationUseCaseFactory
    private lateinit var SUT: SynchronizeNotes

    @BeforeTest
    fun setUp() {
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

    @JsName("DeletedApiNotesNotAddedLocally")
    @Test
    fun `Deleted api notes are not added to the local database`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        assertEquals(1, noteDaoTestFake.notes.count())
    }

    @JsName("DeletedApiNotesNotAddedToApi")
    @Test
    fun `Deleted api notes are not added to the api`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        assertEquals(2, noteApiTestFake.notes.count())
    }

    @JsName("DeletedApiNotesNotUpdatedLocally")
    @Test
    fun `Deleted api notes are not updated in the local database`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(title = "new")
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        assertEquals(1, noteDaoTestFake.notes.count())
    }

    @JsName("DeletedApiNotesAreNotReAddedLocally")
    @Test
    fun `Deleted api notes which were deleted locally are not re-added`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema(wasDeleted = true)
        )

        SUT.executeAsync()

        assertEquals(1, noteDaoTestFake.notes.count())
    }
}
