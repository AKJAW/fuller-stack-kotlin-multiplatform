package com.akjaw.fullerstack.screens.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.NewDeleteNotes
import feature.list.FetchNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Note
import model.NoteIdentifier
import timber.log.Timber

internal class NotesListViewModel(
    private val applicationScope: CoroutineScope,
    private val fetchNotes: FetchNotes,
    private val deleteNotes: NewDeleteNotes
) : ViewModel() {

    internal sealed class NotesListState {
        object Loading : NotesListState()
        object Error : NotesListState()
        data class ShowingList(val notes: List<Note>) : NotesListState()
    }

    private val _viewState = MutableLiveData<NotesListState>(NotesListState.Loading)
    val viewState: LiveData<NotesListState> = _viewState

    fun initializeNotes() = viewModelScope.launch {
        if (viewState.value is NotesListState.ShowingList) return@launch

        fetchNotes.executeAsync().collect(::handleFetchNotesResult)
    }

    private suspend fun handleFetchNotesResult(fetchNotesResult: FetchNotes.Result) {
        when (fetchNotesResult) {
            FetchNotes.Result.Loading -> _viewState.postValue(NotesListState.Loading)
            is FetchNotes.Result.Error -> _viewState.postValue(NotesListState.Error)
            is FetchNotes.Result.Content -> viewModelScope.launch { // TODO exception handling
                fetchNotesResult.notesFlow.collect {
                    _viewState.postValue(NotesListState.ShowingList(it))
                }
            }
        }
    }

    fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) = applicationScope.launch {
        val wasDeleted = deleteNotes.executeAsync(noteIdentifiers)
        if(wasDeleted.not()){
            //TODO there was a problem with synchronization
        }
    }
}
