package com.akjaw.fullerstack.screens.noteslist

import base.usecase.Either
import base.usecase.UseCaseAsync
import com.akjaw.fullerstack.screens.common.ScreenNavigator
import data.Note
import feature.noteslist.FetchNotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesListController(
    private val screenNavigator: ScreenNavigator,
    private val fetchNotes: FetchNotes
) : NotesListViewMvc.Listener {

    private sealed class NotesListState {
        object Uninitialized: NotesListState()
        object Idle: NotesListState() //Maybe showing list?
    }

    private lateinit var viewMvc: NotesListViewMvc
    private lateinit var scope: CoroutineScope
    private var currentState: NotesListState = NotesListState.Uninitialized

    fun bindView(
        viewMvc: NotesListViewMvc,
        scope: CoroutineScope
    ) {
        this.viewMvc = viewMvc
        this.scope = scope
    }

    fun onStart() {
        viewMvc.registerListener(this)
        if(currentState is NotesListState.Uninitialized){
            initializeNotes()
        }
    }

    private fun initializeNotes() {
        viewMvc.showLoading()
        scope.launch {
            fetchNotes.executeAsync(UseCaseAsync.None()) { result ->
                currentState = NotesListState.Idle
                viewMvc.hideLoading()
                when (result) {
                    is Either.Left -> viewMvc.showError() //TODO more elaborate
                    is Either.Right -> listenToNoteChanges(result.r)
                }
            }
        }
    }

    private fun listenToNoteChanges(notesFlow: Flow<List<Note>>) {
        scope.launch {
            notesFlow.collect { notes ->
                viewMvc.setNotes(notes)
            }
        }
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onNoteClicked(title: String) {
        TODO("Not yet implemented")
    }

    override fun onAddNoteClicked() {
        screenNavigator.openAddNoteScreen()
    }

}
