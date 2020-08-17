package feature.synchronization

import base.CommonDispatchers
import com.soywiz.klock.days
import feature.synchronization.SynchronizationTestData.FIRST_NOTE
import feature.synchronization.SynchronizationTestData.SECOND_NOTE
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SynchronizeUpdatedNotesTest {

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: SynchronizeUpdatedNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = SynchronizeUpdatedNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
    }

    @JsName("LocalNewerThenUpdateApi")
    @Test
    fun `Local is more recent then update the API`() = runTest {
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

    @JsName("ApiNewerThenUpdateLocal")
    @Test
    fun `API is more recent then update local database`() = runTest {
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
}
