package com.akjaw.fullerstack.screens.list

import com.akjaw.fullerstack.InstantExecutorExtension
import com.akjaw.fullerstack.getOrAwaitValue
import com.akjaw.fullerstack.screens.list.NotesListViewModel.NotesListState
import feature.list.FetchNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import model.Note
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tests.NoteRepositoryTestFake

@ExtendWith(InstantExecutorExtension::class)
internal class NotesListViewModelTest {

    companion object {
        private val NOTES = listOf(
            Note(id = 1, title = "first", content = "Hey"),
            Note(id = 2, title = "second", content = "Hi")
        )
    }

    private lateinit var repositoryTestFake: NoteRepositoryTestFake
    private lateinit var fetchNotes: FetchNotes
    private lateinit var SUT: NotesListViewModel

    @BeforeEach
    fun setUp() {
        repositoryTestFake = NoteRepositoryTestFake()
        repositoryTestFake.setNotes(NOTES)
        fetchNotes = FetchNotes(TestCoroutineDispatcher(), repositoryTestFake)
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

        repositoryTestFake.setNotes(listOf())
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
        repositoryTestFake.setNotes(NOTES)
    }

    private fun fetchNotesFailure() {
        repositoryTestFake.setNotesFlowError()
    }
}
