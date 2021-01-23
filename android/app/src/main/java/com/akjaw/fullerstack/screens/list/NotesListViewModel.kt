package com.akjaw.fullerstack.screens.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.DeleteNotes
import feature.GetNotes
import feature.local.search.SearchNotes
import feature.local.sort.SortNotes
import feature.synchronization.SynchronizeNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.CreationTimestamp
import model.Note

internal class NotesListViewModel(
    private val applicationScope: CoroutineScope,
    private val getNotes: GetNotes,
    private val deleteNotes: DeleteNotes,
    private val synchronizeNotes: SynchronizeNotes,
    private val searchNotes: SearchNotes,
    private val sortNotes: SortNotes
) : ViewModel() {

    internal sealed class NotesListState {
        object Loading : NotesListState()
        data class ShowingList(val notes: List<Note>) : NotesListState()
    }

    private val searchValueFlow: MutableStateFlow<String> = MutableStateFlow("")
    private val _viewState = MutableLiveData<NotesListState>(NotesListState.Loading)
    val viewState: LiveData<NotesListState> = _viewState

    fun initializeNotes() = viewModelScope.launch {
        if (viewState.value is NotesListState.ShowingList) return@launch

        val notesFlow = getNotes.executeAsync()
        listenToNoteChanges(notesFlow)

        val res = synchronizeNotes.executeAsync()
    }

    private fun listenToNoteChanges(notesFlow: Flow<List<Note>>) = viewModelScope.launch {
        notesFlow.combine(searchValueFlow) { notes, searchValue ->
            val filteredNotes = searchNotes.execute(notes, searchValue)
            filteredNotes
        }.collect {
            _viewState.postValue(NotesListState.ShowingList(it))
        }
    }

    fun deleteNotes(creationTimestamps: List<CreationTimestamp>) = applicationScope.launch {
        deleteNotes.executeAsync(creationTimestamps)
    }

    fun changeSearchValue(text: String) {
        searchValueFlow.value = text
    }
}
