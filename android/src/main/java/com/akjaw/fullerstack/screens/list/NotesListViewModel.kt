package com.akjaw.fullerstack.screens.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.DeleteNotes
import feature.GetNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Note
import model.NoteIdentifier

internal class NotesListViewModel(
    private val applicationScope: CoroutineScope,
    private val getNotes: GetNotes,
    private val deleteNotes: DeleteNotes
) : ViewModel() {

    internal sealed class NotesListState {
        object Loading : NotesListState()
        data class ShowingList(val notes: List<Note>) : NotesListState()
    }

    private val _viewState = MutableLiveData<NotesListState>(NotesListState.Loading)
    val viewState: LiveData<NotesListState> = _viewState

    fun initializeNotes() = viewModelScope.launch {
        if (viewState.value is NotesListState.ShowingList) return@launch

        getNotes.executeAsync().collect {
            _viewState.postValue(NotesListState.ShowingList(it))
        }
    }

    fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) = applicationScope.launch {
        val wasDeleted = deleteNotes.executeAsync(noteIdentifiers)
        if(wasDeleted.not()){
            //TODO there was a problem with synchronization
        }
    }
}
