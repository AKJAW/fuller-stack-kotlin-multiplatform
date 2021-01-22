package com.akjaw.fullerstack.screens.list

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import com.akjaw.fullerstack.screens.common.recyclerview.SpacingItemDecoration
import com.akjaw.fullerstack.screens.common.toParcelable
import com.akjaw.fullerstack.screens.list.recyclerview.ActionRowViewHolder
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapter
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapterFactory
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Note
import org.kodein.di.direct
import org.kodein.di.instance

class NotesListFragment : BaseFragment(R.layout.layout_notes_list) {

    companion object {
        private const val SELECTED_NOTE_IDS = "SELECTED_NOTE_IDS"
    }

    private lateinit var toolbar: Toolbar
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var fab: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesListAdapter: NotesListAdapter
    private val screenNavigator: ScreenNavigator by instance()
    private val notesListActionMode: NotesListActionMode by instance()
    private val notesListAdapterFactory: NotesListAdapterFactory by instance()
    private val viewModel: NotesListViewModel by activityViewModels {
        di.direct.instance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ids = savedInstanceState?.getLongArray(SELECTED_NOTE_IDS)
        notesListAdapter = notesListAdapterFactory.create(
            initialSelectedNotes = ids?.toList(),
            onItemClicked = ::onNoteClicked,
            onSearchInputChange = { Log.d("aaaa", it) }
        )
        viewModel.initializeNotes()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val selectedIds = notesListAdapter.getSelectedNoteIds()
        outState.putLongArray(SELECTED_NOTE_IDS, selectedIds.toLongArray())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.notes_list_toolbar_title)
        notesRecyclerView = view.findViewById(R.id.notes_list)
        loadingIndicator = view.findViewById(R.id.loading_indicator)
        fab = view.findViewById(R.id.floating_action_button)

        fab.setOnClickListener {
            onFabClick()
        }

        notesRecyclerView.apply {
            adapter = notesListAdapter
            recycledViewPool.setMaxRecycledViews(ActionRowViewHolder.VIEW_TYPE, 1)
            notesRecyclerView.setHasFixedSize(true)
            notesRecyclerView.layoutManager = LinearLayoutManager(context)
            val spacing = resources.getDimension(R.dimen.note_item_spacing)
            addItemDecoration(SpacingItemDecoration(spacing.toInt()))
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            render(it)
        }
    }

    private fun onFabClick() {
        notesListActionMode.exitActionMode()
        screenNavigator.openAddNoteScreen()
    }

    private fun render(viewState: NotesListViewModel.NotesListState?) {
        when (viewState) {
            is NotesListViewModel.NotesListState.Loading -> loadingIndicator.visibility = View.VISIBLE
            is NotesListViewModel.NotesListState.ShowingList -> {
                loadingIndicator.visibility = View.INVISIBLE
                notesListAdapter.setNotes(viewState.notes)
            }
        }
    }

    private fun onNoteClicked(note: Note) {
        notesListActionMode.exitActionMode()
        screenNavigator.openEditNoteScreen(note.toParcelable())
    }
}
