package com.akjaw.fullerstack.screens.editor

import com.akjaw.fullerstack.InstantExecutorExtension
import com.akjaw.fullerstack.screens.common.toParcelable
import com.akjaw.fullerstack.testObserve
import database.NoteEntity
import feature.AddNote
import feature.UpdateNote
import helpers.date.UnixTimestampProvider
import helpers.validation.NoteInputValidator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.Note
import network.NoteSchema
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tests.NoteApiTestFake
import tests.NoteDaoTestFake

@ExtendWith(InstantExecutorExtension::class)
internal class NoteEditorViewModelTest {

    companion object {
        private const val TIMESTAMP = 50L
    }

    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var coroutineDispatcher: TestCoroutineDispatcher
    private val unixTimestampProvider: UnixTimestampProvider = mockk {
        every { now() } returns TIMESTAMP
    }
    private val noteInputValidator: NoteInputValidator = mockk()
    private lateinit var SUT: NoteEditorViewModel

    @BeforeEach
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        coroutineDispatcher = TestCoroutineDispatcher()
        val addNote = AddNote(
            coroutineDispatcher = coroutineDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake,
            unixTimestampProvider = unixTimestampProvider
            )
        val updateNote = UpdateNote(
            coroutineDispatcher = coroutineDispatcher,
            unixTimestampProvider = unixTimestampProvider,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake
        )
        SUT = NoteEditorViewModel(TestCoroutineScope(), addNote, updateNote, noteInputValidator)
    }

    @Nested
    inner class CreatingNote {

        @BeforeEach
        fun setUp() {
            noteDaoTestFake.notes = mutableListOf()
            noteApiTestFake.notes = mutableListOf()
        }

        @Test
        fun `If the note title is incorrect then the note is not added`() {
            every { noteInputValidator.isTitleValid(any()) } returns false

            SUT.onActionClicked("", "")

            assertEquals(0, noteDaoTestFake.notes.count())
            assertEquals(0, noteApiTestFake.notes.count())
        }

        @Test
        fun `If the note title is correct then the note is added`() = runBlockingTest {
            every { noteInputValidator.isTitleValid(any()) } returns true

            SUT.onActionClicked("Title", "Content")

            assertEquals(1, noteDaoTestFake.notes.count())
            assertEquals(1, noteApiTestFake.notes.count())
        }

        @Test
        fun `After the note is added a navigation event is set`() {
            every { noteInputValidator.isTitleValid(any()) } returns true
            val wasCalled = SUT.navigationLiveEvent.testObserve()

            SUT.onActionClicked("Title", "Content")

            assertEquals(true, wasCalled())
        }

        @Test
        fun `The correct timestamp is used`() {
            every { noteInputValidator.isTitleValid(any()) } returns true

            SUT.onActionClicked("Title", "Content")

            assertEquals(CreationTimestamp(TIMESTAMP), noteDaoTestFake.notes.first().creationTimestamp)
            assertEquals(LastModificationTimestamp(TIMESTAMP), noteDaoTestFake.notes.first().lastModificationTimestamp)
            assertEquals(CreationTimestamp(TIMESTAMP), noteApiTestFake.notes.first().creationTimestamp)
            assertEquals(LastModificationTimestamp(TIMESTAMP), noteApiTestFake.notes.first().lastModificationTimestamp)
        }
    }

    @Nested
    inner class UpdatingNote {

        private val note = Note(
            title = "title",
            content = "content",
            lastModificationTimestamp = LastModificationTimestamp(0),
            creationTimestamp = CreationTimestamp(0)
        )
        private val entity = NoteEntity(
            localId = -1,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp
        )
        private val schema = NoteSchema(
            apiId = -1,
            title = note.title,
            content = note.content,
            lastModificationTimestamp = note.lastModificationTimestamp,
            creationTimestamp = note.creationTimestamp
        )

        @BeforeEach
        fun setUp() {
            SUT.setNote(note.toParcelable())
            noteDaoTestFake.notes = mutableListOf(entity)
            noteApiTestFake.notes = mutableListOf(schema)
        }

        @Test
        fun `If the note title is incorrect then the note is not updated`() {
            every { noteInputValidator.isTitleValid(any()) } returns false

            SUT.onActionClicked("", "")

            assertEquals(entity, noteDaoTestFake.notes.first())
            assertEquals(schema, noteApiTestFake.notes.first())
        }

        @Test
        fun `If the note title is correct then the note is updated`() {
            every { noteInputValidator.isTitleValid(any()) } returns true
            val updatedTitle = "Changed title"
            val updatedContent = "Changed content"

            SUT.onActionClicked(updatedTitle, updatedContent)

            val updatedEntity = noteDaoTestFake.notes.first()
            assertEquals(updatedTitle, updatedEntity.title)
            assertEquals(updatedContent, updatedEntity.content)

            val updatedSchema = noteApiTestFake.notes.first()
            assertEquals(updatedTitle, updatedSchema.title)
            assertEquals(updatedContent, updatedSchema.content)
        }

        @Test
        fun `After the note is updated a navigation event is set`() {
            every { noteInputValidator.isTitleValid(any()) } returns true
            val wasCalled = SUT.navigationLiveEvent.testObserve()

            SUT.onActionClicked("Title", "Content")

            assertEquals(true, wasCalled())
        }
    }
}
