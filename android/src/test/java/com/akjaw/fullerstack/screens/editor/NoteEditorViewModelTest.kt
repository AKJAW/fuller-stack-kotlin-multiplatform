package com.akjaw.fullerstack.screens.editor

import com.akjaw.fullerstack.InstantExecutorExtension
import com.akjaw.fullerstack.screens.common.toParcelable
import com.akjaw.fullerstack.testObserve
import feature.editor.AddNote
import feature.editor.UpdateNote
import helpers.validation.NoteInputValidator
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.TestCoroutineDispatcher
import model.Note
import model.NoteIdentifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tests.NoteRepositoryTestFake

@ExtendWith(InstantExecutorExtension::class)
internal class NoteEditorViewModelTest {

    private lateinit var repositoryTestFake: NoteRepositoryTestFake
    private lateinit var coroutineDispatcher: TestCoroutineDispatcher
    private val noteInputValidator: NoteInputValidator = mockk()
    private lateinit var SUT: NoteEditorViewModel

    @BeforeEach
    fun setUp() {
        repositoryTestFake = NoteRepositoryTestFake()
        coroutineDispatcher = TestCoroutineDispatcher()
        val addNote = AddNote(coroutineDispatcher, repositoryTestFake)
        val updateNote = UpdateNote(coroutineDispatcher, repositoryTestFake)
        SUT = NoteEditorViewModel(addNote, updateNote, noteInputValidator)
    }

    @Nested
    inner class CreatingNote {

        @BeforeEach
        fun setUp() {
            repositoryTestFake.setNotes(listOf())
        }

        @Test
        fun `If the note title is incorrect then the note is not added`() {
            every { noteInputValidator.isTitleValid(any()) } returns false

            SUT.onActionClicked("", "")

            assertEquals(0, repositoryTestFake.notesList.count())
        }

        @Test
        fun `If the note title is correct then the note is added`() {
            every { noteInputValidator.isTitleValid(any()) } returns true

            SUT.onActionClicked("Title", "Content")

            assertEquals(1, repositoryTestFake.notesList.count())
        }

        @Test
        fun `After the note is added a navigation event is set`() {
            every { noteInputValidator.isTitleValid(any()) } returns true
            val wasCalled = SUT.navigationLiveEvent.testObserve()

            SUT.onActionClicked("Title", "Content")

            assertEquals(true, wasCalled())
        }
    }

    @Nested
    inner class UpdatingNote {

        private val note = Note(NoteIdentifier(1), "title", "content")

        @BeforeEach
        fun setUp() {
            SUT.setNote(note.toParcelable())
            repositoryTestFake.setNotes(listOf(note))
        }

        @Test
        fun `If the note title is incorrect then the note is not updated`() {
            every { noteInputValidator.isTitleValid(any()) } returns false

            SUT.onActionClicked("", "")

            val repositoryNote = repositoryTestFake.notesList.first()
            assertEquals(note.title, repositoryNote.title)
            assertEquals(note.content, repositoryNote.content)
        }

        @Test
        fun `If the note title is correct then the note is updated`() {
            every { noteInputValidator.isTitleValid(any()) } returns true
            val updatedTitle = "Changed title"
            val updatedContent = "Changed content"

            SUT.onActionClicked(updatedTitle, updatedContent)

            val repositoryNote = repositoryTestFake.notesList.first()
            assertEquals(updatedTitle, repositoryNote.title)
            assertEquals(updatedContent, repositoryNote.content)
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
