package feature

import base.CommonDispatchers
import com.soywiz.klock.DateTime
import com.soywiz.klock.days
import database.NoteEntity
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.Note
import model.NoteIdentifier
import network.NoteSchema
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SynchronizeNotesTest {

    companion object {
        private val FIRST_NOTE_DATE = DateTime.createAdjusted(2020, 8, 3)
        private val FIRST_NOTE = Note(
            NoteIdentifier(1),
            title = "first",
            content = "first",
            lastModificationDate = FIRST_NOTE_DATE,
            creationDate = FIRST_NOTE_DATE
        )
        private val SECOND_NOTE_DATE = DateTime.createAdjusted(2020, 8, 4)
        private val SECOND_NOTE = Note(
            NoteIdentifier(2),
            title = "second",
            content = "second",
            lastModificationDate = SECOND_NOTE_DATE,
            creationDate = SECOND_NOTE_DATE
        )
    }

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var SUT: SynchronizeNotes

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = SynchronizeNotes(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
    }

    @JsName("SyncFailedAndLocalModificationRecentThenUpdateApi")
    @Test
    fun `When syncFailed and local modification is more recent then update the API`() = runTest {
        val modificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.createEntity(),
            SECOND_NOTE.createEntity(title = "changed", lastModificationTimestamp = modificationTimestamp, hasSyncFailed = true)
        )
        noteApiTestFake.notes = mutableListOf(FIRST_NOTE.createSchema(), SECOND_NOTE.createSchema())

        SUT.executeAsync()

        assertEquals(FIRST_NOTE.title, noteApiTestFake.notes[0].title)
        assertEquals("changed", noteApiTestFake.notes[1].title)
    }

    @JsName("SyncFailedAndLocalModificationOlderThenUpdateLocal")
    @Test
    fun `When syncFailed and local modification is older then update local database`() = runTest {
        val modificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.createEntity(),
            SECOND_NOTE.createEntity(title = "changed local", hasSyncFailed = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.createSchema(),
            SECOND_NOTE.createSchema(title = "changed api", lastModificationTimestamp = modificationTimestamp)
        )

        SUT.executeAsync()

        assertEquals(FIRST_NOTE.title, noteDaoTestFake.notes[0].title)
        assertEquals("changed api", noteDaoTestFake.notes[1].title)
    }

    @JsName("SyncFailedAndApiDoesntExistThenAddToApi")
    @Test
    fun `When syncFailed and API doesn't have the note then add it to the API`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.createEntity(),
            SECOND_NOTE.createEntity(hasSyncFailed = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.createSchema()
        )

        SUT.executeAsync()

        assertEquals(2, noteApiTestFake.notes.count())
        val addedNote = noteApiTestFake.notes[1]
        assertEquals(SECOND_NOTE.title, addedNote.title)
        assertEquals(SECOND_NOTE.content, addedNote.content)
        assertEquals(CreationTimestamp(SECOND_NOTE.creationDate.unixMillisLong), addedNote.creationTimestamp)
        assertEquals(LastModificationTimestamp(SECOND_NOTE.lastModificationDate.unixMillisLong), addedNote.lastModificationTimestamp)
    }

    @JsName("AddNewApiNotesToLocal")
    @Test
    fun `When API has new notes then add them to the API`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.createEntity()
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.createSchema(),
            SECOND_NOTE.createSchema()
        )

        SUT.executeAsync()

        assertEquals(2, noteApiTestFake.notes.count())
        val addedNote = noteDaoTestFake.notes[1]
        assertEquals(SECOND_NOTE.title, addedNote.title)
        assertEquals(SECOND_NOTE.content, addedNote.content)
        assertEquals(CreationTimestamp(SECOND_NOTE.creationDate.unixMillisLong), addedNote.creationTimestamp)
        assertEquals(LastModificationTimestamp(SECOND_NOTE.lastModificationDate.unixMillisLong), addedNote.lastModificationTimestamp)
    }

    @JsName("WasDeletedAndRecentThenDeleteFromApi")
    @Test
    fun `When wasDeleted and last modification date recent then delete notes from Local and API`() = runTest {
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.createEntity(),
            SECOND_NOTE.createEntity(wasDeleted = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.createSchema(),
            SECOND_NOTE.createSchema()
        )

        SUT.executeAsync()

        assertEquals(1, noteDaoTestFake.notes.count())
        assertEquals(1, noteApiTestFake.notes.count())
    }

    @JsName("WasDeletedAndOlderThenDontDelete")
    @Test
    fun `When wasDeleted and last modification date is older then dont delete notes`() = runTest {
        val modificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.createEntity(),
            SECOND_NOTE.createEntity(wasDeleted = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.createSchema(),
            SECOND_NOTE.createSchema(lastModificationTimestamp = modificationTimestamp)
        )

        SUT.executeAsync()

        assertEquals(2, noteDaoTestFake.notes.count())
        assertEquals(2, noteApiTestFake.notes.count())
    }

    @JsName("WasDeletedAndOlderThenRestoreLocal")
    @Test
    fun `When wasDeleted and last modification date is older then restore local`() = runTest {
        val modificationTimestamp = SECOND_NOTE_DATE.plus(1.days).unixMillisLong
        noteDaoTestFake.notes = listOf(
            FIRST_NOTE.createEntity(),
            SECOND_NOTE.createEntity(wasDeleted = true)
        )
        noteApiTestFake.notes = mutableListOf(
            FIRST_NOTE.createSchema(),
            SECOND_NOTE.createSchema(lastModificationTimestamp = modificationTimestamp)
        )

        SUT.executeAsync()

        assertEquals(false, noteDaoTestFake.notes[1].wasDeleted)
    }

    private fun Note.createEntity(
        title: String? = null,
        content: String? = null,
        lastModificationTimestamp: Long? = null,
        creationTimestamp: Long? = null,
        hasSyncFailed: Boolean = false,
        wasDeleted: Boolean = false
    ) = NoteEntity(
        id = this.noteIdentifier.id,
        noteId = this.noteIdentifier.id,
        title = title ?: this.title,
        content = content ?: this.content,
        lastModificationTimestamp = if (lastModificationTimestamp != null) LastModificationTimestamp(lastModificationTimestamp) else LastModificationTimestamp(this.lastModificationDate.unixMillisLong),
        creationTimestamp = if (creationTimestamp != null) CreationTimestamp(creationTimestamp) else CreationTimestamp(this.creationDate.unixMillisLong),
        hasSyncFailed = hasSyncFailed,
        wasDeleted = wasDeleted
    )

    private fun Note.createSchema(
        title: String? = null,
        content: String? = null,
        lastModificationTimestamp: Long? = null,
        creationTimestamp: Long? = null
    ) = NoteSchema(
        apiId = this.noteIdentifier.id,
        title = title ?: this.title,
        content = content ?: this.content,
        lastModificationTimestamp = if (lastModificationTimestamp != null) LastModificationTimestamp(lastModificationTimestamp) else LastModificationTimestamp(this.lastModificationDate.unixMillisLong),
        creationTimestamp = if (creationTimestamp != null) CreationTimestamp(creationTimestamp) else CreationTimestamp(this.creationDate.unixMillisLong)
    )
}
