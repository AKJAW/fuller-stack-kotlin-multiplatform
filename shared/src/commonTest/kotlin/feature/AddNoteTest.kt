package feature

import base.CommonDispatchers
import com.soywiz.klock.DateTime
import database.NoteEntityMapper
import model.Note
import model.schema.NoteSchema
import network.NoteSchemaMapper
import runTest
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddNoteTest {

    companion object {
        private val date = DateTime.createAdjusted(2020, 7, 28)
        val NOTE_TO_ADD = Note(
            title = "title",
            content = "content",
            creationDate = date,
            lastModificationDate = date
        )
    }

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private val noteEntityMapper: NoteEntityMapper = NoteEntityMapper()
    private val noteSchemaMapper: NoteSchemaMapper = NoteSchemaMapper()
    private lateinit var SUT: NewAddNote

    @BeforeTest
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        SUT = NewAddNote(
            coroutineDispatcher = CommonDispatchers.MainDispatcher,
            noteEntityMapper = noteEntityMapper,
            noteDao = noteDaoTestFake,
            noteSchemaMapper = noteSchemaMapper,
            noteApi = noteApiTestFake
        )
    }

    @JsName("AddsTheNoteToTheLocalDatabase")
    @Test
    fun `Adds the note to the local database`() = runTest {

        SUT.executeAsync(NOTE_TO_ADD)

        val expectedNote = noteEntityMapper.toEntity(NOTE_TO_ADD)
            .copy( //TODO should this test be concerned with the ids
                id = 0,
                noteId = 0
            )
        assertEquals(expectedNote, noteDaoTestFake.notes.first())
    }

    @JsName("AddsTheNoteToTheAPI")
    @Test
    fun `Adds the note to the API`() = runTest {

        SUT.executeAsync(NOTE_TO_ADD)

        val expectedNote = noteSchemaMapper.toSchema(NOTE_TO_ADD)
            .copy(apiId = 0)
        assertEquals(expectedNote, noteApiTestFake.notes.first())
    }

    @JsName("LocalDatabaseIdUpdated")
    @Test
    fun `Local database id is updated after successful API response`() = runTest {
        noteApiTestFake.notes.addAll(listOf(NoteSchema(apiId = 0), NoteSchema(apiId = 1)))

        SUT.executeAsync(NOTE_TO_ADD)

        val expectedNote = noteEntityMapper.toEntity(NOTE_TO_ADD)
            .copy(
                id = 0,
                noteId = 2
            )
        assertEquals(expectedNote, noteDaoTestFake.notes.first())
    }

    //When a request fails then the entity has a property reflecting that
    @JsName("SyncFailedIsSet")
    @Test
    fun `When request fails then set sync failed in the local database`() = runTest {
        noteApiTestFake.willFail = true

        SUT.executeAsync(NOTE_TO_ADD)

        assertEquals(true, noteDaoTestFake.notes.first().hasSyncFailed)
    }

}