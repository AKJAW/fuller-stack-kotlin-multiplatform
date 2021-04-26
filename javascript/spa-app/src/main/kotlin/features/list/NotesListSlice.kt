package features.list

import DexieNoteDao
import composition.KodeinEntry
import feature.local.search.SearchNotes
import feature.local.sort.SortNotes
import feature.local.sort.SortProperty
import features.list.thunk.GetNotesThunk
import features.list.thunk.SynchronizeNotesThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import model.Note
import org.kodein.di.instance
import redux.RAction
import store.RThunk

object NotesListSlice {
    data class State(
        val originalList: Array<Note> = emptyArray(),
        val notesList: Array<Note> = emptyArray(),
        val isLoading: Boolean = true,
        val sortProperty: SortProperty = SortProperty.DEFAULT,
        val searchValue: String = "",
    )

    private val dexieNoteDao by KodeinEntry.di.instance<DexieNoteDao>()
    private val sortNotes by KodeinEntry.di.instance<SortNotes>()
    private val searchNotes by KodeinEntry.di.instance<SearchNotes>()

    private val notesListScope = CoroutineScope(SupervisorJob())
    private val getNotesListThunk = GetNotesThunk(notesListScope, dexieNoteDao)
    private val synchronizeNotesThunk = SynchronizeNotesThunk(GlobalScope, dexieNoteDao)

    init {
        notesListScope.launch {
            dexieNoteDao.initialize()
        }
    }

    fun getNotesList(): RThunk = getNotesListThunk

    fun synchronizeNotes(): RThunk = synchronizeNotesThunk

    class SetNotesList(val notesList: Array<Note>) : RAction

    class SetIsLoading(val isLoading: Boolean) : RAction

    class SetSortProperty(val sortProperty: SortProperty) : RAction

    class SetSearchValue(val searchValue: String) : RAction

    fun reducer(state: State = State(), action: RAction): State {
        return when (action) {
            is SetNotesList -> {
                val notesList = state.calculateNotes(notes = action.notesList.toList())
                state.copy(originalList = action.notesList, notesList = notesList, isLoading = false)
            }
            is SetIsLoading -> {
                state.copy(isLoading = action.isLoading)
            }
            is SetSortProperty -> {
                val notesList = state.calculateNotes(sortProperty = action.sortProperty)
                state.copy(notesList = notesList, sortProperty = action.sortProperty)
            }
            is SetSearchValue -> {
                val notesList = state.calculateNotes(searchValue = action.searchValue)
                state.copy(notesList = notesList, searchValue = action.searchValue)
            }
            else -> state
        }
    }

    private fun State.calculateNotes(
        notes: List<Note> = this.originalList.toList(),
        searchValue: String = this.searchValue,
        sortProperty: SortProperty = this.sortProperty,
    ): Array<Note> {
        val filteredNotes = searchNotes.execute(notes, searchValue)
        val sortedNotes = sortNotes.execute(filteredNotes, sortProperty)
        return sortedNotes.toTypedArray()
    }
}
