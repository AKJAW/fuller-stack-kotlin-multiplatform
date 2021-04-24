package com.akjaw.fullerstack.screens.list

import com.akjaw.fullerstack.InstantExecutorExtension
import com.akjaw.fullerstack.getOrAwaitValue
import com.akjaw.fullerstack.screens.list.NotesListViewModel.NotesListState
import com.soywiz.klock.DateFormat
import database.NoteEntityMapper
import feature.DeleteNotes
import feature.GetNotes
import feature.local.search.SearchNotes
import feature.local.sort.SortNotes
import feature.local.sort.SortProperty
import feature.local.sort.SortDirection
import feature.local.sort.SortType
import feature.synchronization.*
import helpers.date.NoteDateFormat
import helpers.date.PatternProvider
import helpers.date.UnixTimestampProvider
import helpers.date.toDateFormat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import model.CreationTimestamp
import model.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import tests.NoteApiTestFake
import tests.NoteDaoTestFake
import java.util.stream.Stream

@ExtendWith(InstantExecutorExtension::class)
internal class NotesListViewModelTest {

    companion object {
        private val NOTES = listOf(
            Note(title = "first", content = "Hey", creationTimestamp = CreationTimestamp(3)),
            Note(title = "second", content = "Hi", creationTimestamp = CreationTimestamp(2)),
            Note(title = "third", content = "Hello", creationTimestamp = CreationTimestamp(1))
        )
    }

    private val noteEntityMapper = NoteEntityMapper()
    private lateinit var noteDaoTestFake: NoteDaoTestFake
    private lateinit var noteApiTestFake: NoteApiTestFake
    private val unixTimestampProvider: UnixTimestampProvider = mockk {
        every { now() } returns 50L
    }
    private lateinit var getNotes: GetNotes
    private lateinit var deleteNotes: DeleteNotes
    private lateinit var synchronizeNotes: SynchronizeNotes
    private lateinit var SUT: NotesListViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope()

    private val patternProvider: PatternProvider = mockk {
        every { patternFlow } returns flow { emit(NoteDateFormat.Default.toDateFormat()) }
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        noteDaoTestFake = NoteDaoTestFake()
        noteApiTestFake = NoteApiTestFake()
        getNotes = GetNotes(testCoroutineDispatcher, noteDaoTestFake, noteEntityMapper)
        deleteNotes = DeleteNotes(testCoroutineDispatcher, unixTimestampProvider, noteDaoTestFake, noteApiTestFake)
        synchronizeNotes = SynchronizeApiAndLocalNotes(
            coroutineDispatcher = testCoroutineDispatcher,
            noteDao = noteDaoTestFake,
            noteApi = noteApiTestFake,
            synchronizeDeletedNotes = SynchronizeDeletedNotes(testCoroutineDispatcher, noteDaoTestFake, noteApiTestFake, unixTimestampProvider),
            synchronizeAddedNotes = SynchronizeAddedNotes(testCoroutineDispatcher, noteDaoTestFake, noteApiTestFake),
            synchronizeUpdatedNotes = SynchronizeUpdatedNotes(testCoroutineDispatcher, noteDaoTestFake, noteApiTestFake),
        )
        SUT = NotesListViewModel(
            applicationScope = testCoroutineScope,
            getNotes = getNotes,
            deleteNotes = deleteNotes,
            synchronizeNotes = synchronizeNotes,
            searchNotes = SearchNotes(),
            sortNotes = SortNotes(),
            patternProvider = patternProvider
        )

        noteDaoTestFake.initializeNoteEntities(NOTES)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
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

    @ParameterizedTest
    @ArgumentsSource(SearchArgumentsProvider::class)
    fun `Depending on the search value, the notes are filtered`(
        searchValue: String,
        expectedNotes: List<Note>
    ) {
        SUT.initializeNotes()

        SUT.changeSearchValue(searchValue)

        val viewState = SUT.viewState.getOrAwaitValue()
        val expectedViewState = NotesListState.ShowingList(expectedNotes)
        assertEquals(expectedViewState, viewState)
    }

    private class SearchArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of("first", listOf(NOTES[0])),
                Arguments.of("ir", listOf(NOTES[0], NOTES[2])),
                Arguments.of("fourth", listOf<Note>()),
                Arguments.of("", NOTES),
            )
        }
    }

    @Test
    fun `Changing the sort type changes the notes sorting`() {
        SUT.initializeNotes()

        SUT.changeSortProperty(SortProperty(SortType.NAME, SortDirection.DESCENDING))

        val viewState = SUT.viewState.getOrAwaitValue()
        val expectedViewState = NotesListState.ShowingList(listOf(NOTES[2], NOTES[1], NOTES[0]))
        assertEquals(expectedViewState, viewState)
    }

    @ParameterizedTest
    @ArgumentsSource(DateFormatArgumentsProvider::class)
    fun `Depending on the patternFlow, the notes have that date format`(
        dateFormat: DateFormat
    ) {
        every { patternProvider.patternFlow } returns flow { emit(dateFormat) }
        SUT.initializeNotes()

        val viewState = SUT.viewState.getOrAwaitValue() as? NotesListState.ShowingList
        assertEquals(dateFormat, viewState?.notes?.getOrNull(0)?.dateFormat)
        assertEquals(dateFormat, viewState?.notes?.getOrNull(1)?.dateFormat)
        assertEquals(dateFormat, viewState?.notes?.getOrNull(2)?.dateFormat)
    }

    private class DateFormatArgumentsProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                Arguments.of(DateFormat(NoteDateFormat.NOTES_LIST_ITEM_MONTH.value)),
                Arguments.of(DateFormat(NoteDateFormat.NOTES_LIST_ITEM_MONTH_HOUR.value)),
                Arguments.of(DateFormat(NoteDateFormat.NOTES_LIST_ITEM_YEAR.value)),
                Arguments.of(DateFormat(NoteDateFormat.NOTES_LIST_ITEM_YEAR_HOUR.value)),
            )
        }
    }
}
