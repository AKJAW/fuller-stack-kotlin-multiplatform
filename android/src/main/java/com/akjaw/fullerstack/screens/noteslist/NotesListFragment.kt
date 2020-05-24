package com.akjaw.fullerstack.screens.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.erased.instance
import usecases.FetchNotesListUseCaseAsync

@Suppress("MagicNumber")
class NotesListFragment : BaseFragment(), NotesListViewMvc.Listener, FetchNotesListUseCaseAsync.Listener {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private val fetchNotesListUseCaseAsync: FetchNotesListUseCaseAsync by instance<FetchNotesListUseCaseAsync>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        viewMvc.registerListener(this)
        fetchNotesListUseCaseAsync.registerListener(this)

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                fetchNotesListUseCaseAsync.run()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
        fetchNotesListUseCaseAsync.unregisterListener(this)
    }

    override fun onNoteClicked(title: String) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
    }

    override fun onNoteListFetchSuccess(notes: List<Note>) {
        viewMvc.setNotes(notes)
    }

    override fun onNoteListFetchFail() {
        TODO("Not yet implemented")
    }
}
