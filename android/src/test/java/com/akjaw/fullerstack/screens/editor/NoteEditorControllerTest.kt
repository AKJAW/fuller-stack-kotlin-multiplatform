package com.akjaw.fullerstack.screens.editor

import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import com.akjaw.fullerstack.screens.common.toParcelable
import data.Note
import feature.noteslist.AddNote
import feature.noteslist.UpdateNote
import helpers.validation.NoteInputValidator
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class NoteEditorControllerTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private val viewMvc: NoteEditorViewMvc = mockk {
        every { rootView.context } returns mockk()
        every { setAddToolbarTitle() } answers {}
        every { registerListener(any()) } answers {}
        every { unregisterListener(any()) } answers {}
        every { showNoteTitleError(any()) } answers {}
        every { setUpdateToolbarTitle() } answers {}
        every { setNoteTitle(any()) } answers {}
        every { setNoteContent(any()) } answers {}
        every { hideKeyboard() } answers {}
    }
    private val screenNavigator: ScreenNavigator = mockk { every { goBack(any()) } answers {} }
    private val addNote: AddNote = mockk()
    private val updateNote: UpdateNote = mockk()
    private val noteInputValidator: NoteInputValidator = mockk()
    private lateinit var SUT: NoteEditorController

    @BeforeEach
    fun setUp() {
        SUT = NoteEditorController(addNote, updateNote, noteInputValidator, screenNavigator)
        SUT.bindView(viewMvc, testScope, null)
    }

    @Test
    fun `toolbar title is set on initialization`() {
        verify {
            viewMvc.setAddToolbarTitle()
        }
    }

    @Test
    fun `onStart registers to the view`() {
        SUT.onStart()

        verify {
            viewMvc.registerListener(SUT)
        }
    }

    @Test
    fun `onStop() unregisters from the view`() {
        SUT.onStop()

        verify {
            viewMvc.unregisterListener(SUT)
        }
    }

    @Nested
    inner class CreatingNote {

        @BeforeEach
        fun setUp() {
            SUT.bindView(viewMvc, testScope, null)
        }

        @Test
        fun `Positive action doesnt add a note if the title is incorrect`() {
            every { noteInputValidator.isTitleValid(any()) } returns false
            every { viewMvc.getNoteTitle() } returns ""
            every { viewMvc.getNoteContent() } returns ""

            SUT.onActionClicked()

            coVerify(exactly = 0) {
                addNote.executeAsync(any(), any())
            }
        }

        @Test
        fun `Positive action adds the note if the title is correct`() {
            every { noteInputValidator.isTitleValid(any()) } returns true
            every { viewMvc.getNoteTitle() } returns "title"
            every { viewMvc.getNoteContent() } returns "content"

            SUT.onActionClicked()

            coVerify(exactly = 1) {
                addNote.executeAsync(any(), any())
            }
        }
    }

    @Nested
    inner class UpdatingNote {

        private val note = Note(1, "title", "content")

        @BeforeEach
        fun setUp() {
            SUT.bindView(viewMvc, testScope, note.toParcelable())

            every { noteInputValidator.isTitleValid(any()) } returns true
            every { viewMvc.getNoteTitle() } returns note.title
            every { viewMvc.getNoteContent() } returns note.content
        }

        @Test
        fun `Positive action edits the note`() {
            SUT.onActionClicked()

            coVerify(exactly = 1) {
                updateNote.executeAsync(any(), any())
            }
        }
    }

    @Test
    fun `Negative action navigates back`() {
        SUT.onCancelClicked()

        verify {
            screenNavigator.goBack(any())
        }
    }
}
