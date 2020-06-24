package com.akjaw.fullerstack.screens.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import data.Note
import helpers.date.PatternProvider

class NotesListViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    viewMvcFactory: ViewMvcFactory,
    patternProvider: PatternProvider
) : NotesListViewMvc() {

    override val rootView: View = inflater.inflate(R.layout.layout_notes_list, parent, false)
    private val notesListAdapter: NotesListAdapter = NotesListAdapter(
        ::onNoteClicked,
        viewMvcFactory,
        patternProvider.getNotesListItemPattern()
    )
    private val toolbar: Toolbar = findViewById(R.id.toolbar)
    private val notesRecyclerView: RecyclerView = rootView.findViewById(R.id.notes_list)
    private val loadingIndicator: ProgressBar = rootView.findViewById(R.id.loading_indicator)
    private val fab: FloatingActionButton = rootView.findViewById(R.id.floating_action_button)

    init {
        toolbar.title = context.getString(R.string.notes_list_toolbar_title)
        notesRecyclerView.adapter = notesListAdapter
        notesRecyclerView.setHasFixedSize(true)
        notesRecyclerView.layoutManager = LinearLayoutManager(context)

        fab.setOnClickListener {
            listeners.forEach { it.onAddNoteClicked() }
        }
    }

    private fun onNoteClicked(title: String) {
        listeners.forEach { listeners ->
            listeners.onNoteClicked(title)
        }
    }

    override fun setNotes(notes: List<Note>) {
        notesListAdapter.setNotes(notes)
    }

    override fun showLoading() {
        loadingIndicator.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loadingIndicator.visibility = View.GONE
    }

    override fun showError() {
        TODO("Not yet implemented")
    }

}
