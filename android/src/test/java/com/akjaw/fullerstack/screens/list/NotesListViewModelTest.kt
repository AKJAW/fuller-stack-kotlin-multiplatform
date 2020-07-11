package com.akjaw.fullerstack.screens.list

import base.usecase.Either
import base.usecase.Failure
import com.akjaw.fullerstack.InstantExecutorExtension
import com.akjaw.fullerstack.getOrAwaitValue
import com.akjaw.fullerstack.screens.list.NotesListViewModel.NotesListState
import feature.list.FetchNotes
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.setMain
import model.Note
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
internal class NotesListViewModelTest {

    companion object {
        private val NOTES = listOf(
            Note(id = 1, title = "first", content = "Hey"),
            Note(id = 2, title = "second", content = "Hi")
        )
    }

    private val fetchNotes: FetchNotes = mockk()
    private val notesListStateFlow = MutableStateFlow(NOTES)
    private lateinit var SUT: NotesListViewModel

    @BeforeEach
    fun setUp() {
        SUT = NotesListViewModel(fetchNotes)
    }

    @Test
    fun `fetching shows loading at the start`() {
        Dispatchers.setMain(Dispatchers.Default)
        fetchNotesSuccess()

        SUT.initializeNotes()

        val viewState = SUT.viewState.getOrAwaitValue()
        assertEquals(NotesListState.Loading, viewState)
    }

    @Test
    fun `successful fetch shows the notes list`() {
        fetchNotesSuccess()

        SUT.initializeNotes()

        val viewState = SUT.viewState.getOrAwaitValue()
        val expectedViewState = NotesListState.ShowingList(NOTES)
        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `notes list changes are shown in the view`() {
        fetchNotesSuccess()

        SUT.initializeNotes()

        assertEquals(
            NotesListState.ShowingList(NOTES),
            SUT.viewState.getOrAwaitValue()
        )

        notesListStateFlow.value = listOf()
        assertEquals(
            NotesListState.ShowingList(listOf()),
            SUT.viewState.getOrAwaitValue()
        )
    }

    @Test
    fun `fetch error is shown in the view`() {
        fetchNotesFailure()

        SUT.initializeNotes()

        val viewState = SUT.viewState.getOrAwaitValue()
        assertEquals(NotesListState.Error, viewState)
    }

    private fun fetchNotesSuccess() {
        coEvery { fetchNotes.executeAsync() } returns Either.Right(notesListStateFlow as Flow<List<Note>>)
    }

    private fun fetchNotesFailure() {
        coEvery { fetchNotes.executeAsync() } returns Either.Left(Failure.ServerError)
    }
}
