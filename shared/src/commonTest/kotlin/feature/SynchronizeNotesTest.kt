package feature

import base.CommonDispatchers
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import feature.synchronization.SynchronizationUseCaseFactory
import feature.synchronization.copyToEntity
import feature.synchronization.copyToSchema
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
    private lateinit var synchronizationUseCaseFactory: SynchronizationUseCaseFactory
    private lateinit var SUT: SynchronizeNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        synchronizationUseCaseFactory = SynchronizationUseCaseFactory(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        SUT = synchronizationUseCaseFactory.createSynchronizeNotes()
    }

    @JsName("AddNewApiNotesToLocal")
    @Test
    fun `When API has new notes then add them to the API`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema()
        )

        SUT.executeAsync()

        assertEquals(2, noteApiTestFake.notes.count())
        val addedNote = noteDaoTestFake.notes[1]
        assertEquals(SECOND_NOTE.title, addedNote.title)
        assertEquals(SECOND_NOTE.content, addedNote.content)
        assertEquals(SECOND_NOTE.creationTimestamp, addedNote.creationTimestamp)
        assertEquals(SECOND_NOTE.lastModificationTimestamp, addedNote.lastModificationTimestamp)
    }
}
