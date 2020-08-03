package com.akjaw.fullerstack.screens.list

import com.akjaw.fullerstack.InstantExecutorExtension
import com.akjaw.fullerstack.getOrAwaitValue
import com.akjaw.fullerstack.screens.list.NotesListViewModel.NotesListState
import database.NoteEntityMapper
import feature.NewDeleteNotes
import feature.NewGetNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.setMain
import model.Note
import model.NoteIdentifier
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import tests.NoteApiTestFake
import tests.NoteDaoTestFake

@ExtendWith(InstantExecutorExtension::class)
internal class NotesListViewModelTest {

    companion object {
        private val NOTES = listOf(
            Note(noteIdentifier = NoteIdentifier(1), title = "first", content = "Hey"),
            Note(noteIdentifier = NoteIdentifier(2), title = "second", content = "Hi")
        )
    }

    private val noteEntityMapper = NoteEntityMapper()
    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private lateinit var getNotes: NewGetNotes
    private lateinit var deleteNotes: NewDeleteNotes
    private lateinit var SUT: NotesListViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope()

    @BeforeEach
    fun setUp() {
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        getNotes = NewGetNotes(testCoroutineDispatcher, noteDaoTestFake, noteEntityMapper)
        deleteNotes = NewDeleteNotes(testCoroutineDispatcher, noteDaoTestFake, noteApiTestFake)
        SUT = NotesListViewModel(testCoroutineScope, getNotes, deleteNotes)

        noteDaoTestFake.initializeNoteEntities(NOTES)
    }

    @Test
    fun `fetching shows loading at the start`() {
        Dispatchers.setMain(Dispatchers.Default)

        SUT.initializeNotes()

        val viewState = SUT.viewState.getOrAwaitValue()
        assertEquals(NotesListState.Loading, viewState)
    }

    @Test
    fun `successful fetch shows the notes list`() {

        SUT.initializeNotes()

        val viewState = SUT.viewState.getOrAwaitValue()
        val expectedViewState = NotesListState.ShowingList(NOTES)
        assertEquals(expectedViewState, viewState)
    }

    @Test
    fun `notes list changes are shown in the view`() {

        SUT.initializeNotes()

        assertEquals(
            NotesListState.ShowingList(NOTES),
            SUT.viewState.getOrAwaitValue()
        )

        noteDaoTestFake.notes = listOf()
        assertEquals(
            NotesListState.ShowingList(listOf()),
            SUT.viewState.getOrAwaitValue()
        )
    }
}
