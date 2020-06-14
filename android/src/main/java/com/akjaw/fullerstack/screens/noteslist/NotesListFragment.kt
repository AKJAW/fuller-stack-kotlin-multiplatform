package com.akjaw.fullerstack.screens.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.editor.NoteEditorDialog
import data.Note
import feature.noteslist.AddNote
import feature.noteslist.FetchNotes
import feature.noteslist.RefreshNotes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.kodein.di.instance

class NotesListFragment : BaseFragment(), NotesListViewMvc.Listener {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private val refreshNotes: RefreshNotes by instance<RefreshNotes>()
    private val fetchNotes: FetchNotes by instance<FetchNotes>()
    private val addNote: AddNote by instance<AddNote>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)

        fetchNotes()
    }

    private fun fetchNotes() {
        viewMvc.showLoading()
        lifecycleScope.launch {
            fetchNotes.executeAsync(UseCaseAsync.None()) { either ->
                viewMvc.hideLoading()
                when(either) {
                    is Either.Left -> onFetchNotesFail(either.l)
                    is Either.Right -> listenToNotesChanges(either.r)
                }
            }
        }
    }

    private fun onFetchNotesFail(failure: Failure) {
        TODO("Not yet implemented")
    }

    private fun listenToNotesChanges(notesFlow: Flow<List<Note>>) {
        lifecycleScope.launch {
            notesFlow.collect { notes ->
                viewMvc.setNotes(notes)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun onNoteClicked(title: String) {
        TODO("Not implemented")
    }

    override fun onAddNoteClicked() {
        fragmentManager?.let { fragmentManager ->
            val dialog = NoteEditorDialog.newNoteEditorDialog()

            dialog.show(fragmentManager, null)
        }
    }
}
