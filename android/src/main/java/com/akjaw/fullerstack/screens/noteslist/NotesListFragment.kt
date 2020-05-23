package com.akjaw.fullerstack.screens.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import data.Note
import org.kodein.di.erased.instance

class NotesListFragment : BaseFragment(), NotesListViewMvc.Listener {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)

        val notes = List(10) { Note(it.toString()) }
        viewMvc.setNotes(notes)

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()

        viewMvc.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        viewMvc.unregisterListener(this)
    }

    override fun onNoteClicked(title: String) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show()
    }
}
