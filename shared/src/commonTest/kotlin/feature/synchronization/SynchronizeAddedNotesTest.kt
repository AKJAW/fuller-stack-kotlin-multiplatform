package feature.synchronization

import base.CommonDispatchers
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SynchronizeAddedNotesTest {

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: SynchronizeAddedNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = SynchronizeAddedNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
    }

    @JsName("DatabaseNewNotesAddToApi")
    @Test
    fun `When local database has new notes the add them to the api`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema()
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(2, noteApiTestFake.notes.count())
        val addedNote = noteApiTestFake.notes[1]
        assertEquals(SECOND_NOTE.title, addedNote.title)
        assertEquals(SECOND_NOTE.content, addedNote.content)
        assertEquals(SECOND_NOTE.creationTimestamp, addedNote.creationTimestamp)
        assertEquals(SECOND_NOTE.lastModificationTimestamp, addedNote.lastModificationTimestamp)
    }

    @JsName("DatabaseNewNotesApiSuccessUpdateHasSyncFailed")
    @Test
    fun `When local database has new notes after adding them to API hasSyncFailed is set to false`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity(),
            SECOND_NOTE.copyToEntity(hasSyncFailed = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema()
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)

        assertEquals(false, noteDaoTestFake.notes[1].hasSyncFailed)
    }

    @JsName("ApiNewNotesAddToDatabase")
    @Test
    fun `When API has new notes the add them locally`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.copyToEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.copyToSchema(),
            SECOND_NOTE.copyToSchema()
        )

        SUT.executeAsync(noteDaoTestFake.notes, noteApiTestFake.notes)
        assertEquals(2, noteDaoTestFake.notes.count())
        val addedNote = noteDaoTestFake.notes[1]
        assertEquals(SECOND_NOTE.title, addedNote.title)
        assertEquals(SECOND_NOTE.content, addedNote.content)
        assertEquals(SECOND_NOTE.creationTimestamp, addedNote.creationTimestamp)
        assertEquals(SECOND_NOTE.lastModificationTimestamp, addedNote.lastModificationTimestamp)
    }
}
