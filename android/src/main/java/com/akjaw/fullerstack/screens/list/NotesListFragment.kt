package com.akjaw.fullerstack.screens.list

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.helpers.viewmodel.viewModels
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import com.akjaw.fullerstack.screens.common.toParcelable
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapter
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapterFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Note
import org.kodein.di.instance

class NotesListFragment : BaseFragment(R.layout.layout_notes_list) {

    private lateinit var toolbar: Toolbar
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var fab: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesListAdapter: NotesListAdapter
    private val screenNavigator: ScreenNavigator by instance()
    private val notesListAdapterFactory: NotesListAdapterFactory by instance()
    private val viewModel: NotesListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesListAdapter = notesListAdapterFactory.create(::onNoteClicked)
        viewModel.initializeNotes()
    }

    private fun onNoteClicked(note: Note) {
        screenNavigator.openEditNoteScreen(requireContext(), note.toParcelable())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = requireContext().getString(R.string.notes_list_toolbar_title)
        notesRecyclerView = view.findViewById(R.id.notes_list)
        loadingIndicator = view.findViewById(R.id.loading_indicator)
        fab = view.findViewById(R.id.floating_action_button)

        fab.setOnClickListener {
            screenNavigator.openAddNoteScreen(requireContext())
        }

        notesRecyclerView.apply {
            adapter = notesListAdapter
            notesRecyclerView.setHasFixedSize(true)
            notesRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        viewModel.viewState.observe(
            viewLifecycleOwner,
            Observer {
                render(it)
            }
        )
    }

    private fun render(viewState: NotesListViewModel.NotesListState?) {
        when (viewState) {
            NotesListViewModel.NotesListState.Loading -> loadingIndicator.visibility = View.VISIBLE
            NotesListViewModel.NotesListState.Error -> TODO()
            is NotesListViewModel.NotesListState.ShowingList -> {
                loadingIndicator.visibility = View.INVISIBLE
                notesListAdapter.setNotes(viewState.notes)
            }
            null -> TODO()
        }
    }

    override fun onStart() {
        super.onStart()
    }
}
