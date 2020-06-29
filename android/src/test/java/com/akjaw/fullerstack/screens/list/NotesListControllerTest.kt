package com.akjaw.fullerstack.screens.list

import base.usecase.Either
import base.usecase.Failure
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import com.akjaw.fullerstack.screens.common.toParcelable
import data.Note
import feature.noteslist.FetchNotes
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class NotesListControllerTest {

    companion object {
        private val NOTES = listOf(
            Note("first", "Hey"),
            Note("second", "Hi")
        )
    }

    private val testDispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)
    private val viewMvc: NotesListViewMvc = mockk {
        every { rootView.context } returns mockk()
        every { showLoading() } answers {}
        every { hideLoading() } answers {}
        every { registerListener(any()) } answers {}
        every { unregisterListener(any()) } answers {}
    }
    private val screenNavigator: ScreenNavigator = mockk() {
        every { openAddNoteScreen(any()) } answers {}
        every { openEditNoteScreen(any(), any()) } answers {}
    }
    private val fetchNotes: FetchNotes = mockk()
    private lateinit var SUT: NotesListController

    @BeforeEach
    fun setUp(){
        SUT = NotesListController(screenNavigator, fetchNotes)
        SUT.bindView(viewMvc, testScope)
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `onAddNoteClicked opens the add note screen`(){
        SUT.onAddNoteClicked()

        verify {
            screenNavigator.openAddNoteScreen(any())
        }
    }

    @Test
    fun `onNoteClicked opens the edit note screen`(){
        val note = Note("1", "one")

        SUT.onNoteClicked(note)

        verify {
            screenNavigator.openEditNoteScreen(any(), note.toParcelable())
        }
    }

    @Nested
    inner class OnStartTest{
        @Test
        fun `onStart call fetches the notes list`() {
            fetchNotesSuccess()
            SUT.onStart()
            coVerify { fetchNotes.executeAsync(any(), any()) }
        }

        @Test
        fun `onStart consecutive calls also fetch the notes list`() {
            fetchNotesSuccess()
            SUT.onStart()
            SUT.onStart()
            SUT.onStart()
            coVerify(exactly = 3) { fetchNotes.executeAsync(any(), any()) }
        }

        @Test
        fun `onStart shows a loading indicator when notes are fetched`() {
            fetchNotesSuccess()
            SUT.onStart()
            verify {
                viewMvc.showLoading()
            }
        }

        @Test
        fun `onStart fetch notes success shows the list in the view and hides the loading indicator`() {
            fetchNotesSuccess()
            SUT.onStart()
            verify {
                viewMvc.hideLoading()
                viewMvc.setNotes(NOTES)
            }
        }

        @Test
        fun `onStart fetch notes failure displays an error and hides the loading indicator`() {
            fetchNotesFailure()
            SUT.onStart()
            verify {
                viewMvc.showError()
                viewMvc.hideLoading()
            }
        }

        @Test
        fun `onStart registers to the view`() {
            SUT.onStart()
            verify {
                viewMvc.registerListener(SUT)
            }
        }
    }

    @Nested
    inner class OnStopTest {

        @Test
        fun `onStop unregisters from the view`() {
            SUT.onStop()
            verify {
                viewMvc.unregisterListener(SUT)
            }
        }
    }

    private fun fetchNotesSuccess() {
        coEvery { fetchNotes.executeAsync(any(), any()) } answers {
            val flow = flow { emit(NOTES) }
            secondArg<(Either<Failure, Flow<List<Note>>>) -> Unit>().invoke(Either.Right(flow))
        }
    }

    private fun fetchNotesFailure() {
        coEvery { fetchNotes.executeAsync(any(), any()) } answers {
            secondArg<(Either<Failure, Flow<List<Note>>>) -> Unit>().invoke(Either.Left(Failure.ServerError))
        }
    }

}