package com.akjaw.fullerstack.screens.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import base.usecase.Either
import base.usecase.Failure
import feature.list.FetchNotes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Note

internal class NotesListViewModel(
    private val fetchNotes: FetchNotes
) : ViewModel() {

    internal sealed class NotesListState {
        object Loading : NotesListState()
        object Error : NotesListState()
        data class ShowingList(val notes: List<Note>) : NotesListState()
    }

    private val _viewState = MutableLiveData<NotesListState>(NotesListState.Loading)
    val viewState: LiveData<NotesListState> = _viewState

    fun initializeNotes() = viewModelScope.launch {
        _viewState.postValue(NotesListState.Loading)
        val result= fetchNotes.executeAsync()
        handleResult(result)
    }

    fun initializeNotesTest() = viewModelScope.launch {
        if(viewState.value is NotesListState.ShowingList) return@launch

        fetchNotes.executeTest().collect(::handleFetchNotesResult)
    }

    private suspend fun handleFetchNotesResult(fetchNotesResult: FetchNotes.Result) {
        when (fetchNotesResult) {
            FetchNotes.Result.Loading -> _viewState.postValue(NotesListState.Loading)
            is FetchNotes.Result.Error -> _viewState.postValue(NotesListState.Error)
            is FetchNotes.Result.Content -> viewModelScope.launch { //TODO exception handling
                fetchNotesResult.notesFlow.collect {
                    _viewState.postValue(NotesListState.ShowingList(it))
                }
            }
        }
    }

    private fun handleResult(result: Either<Failure, Flow<List<Note>>>) {
        when (result) {
            is Either.Right -> viewModelScope.launch {
                result.r.collect {
                    _viewState.postValue(NotesListState.ShowingList(it))
                }
            }
            is Either.Left -> _viewState.postValue(NotesListState.Error)
        }
    }
}
