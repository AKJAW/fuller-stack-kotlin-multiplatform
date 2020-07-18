package com.akjaw.fullerstack.screens.list

import android.os.Bundle
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
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapter
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapterFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import model.Note
import org.kodein.di.direct
import org.kodein.di.instance


class NotesListFragment : BaseFragment(R.layout.layout_notes_list) {

    private lateinit var toolbar: Toolbar
    private lateinit var loadingIndicator: ProgressBar
    private lateinit var fab: FloatingActionButton
    private lateinit var notesRecyclerView: RecyclerView
    private lateinit var notesListAdapter: NotesListAdapter
    private val screenNavigator: ScreenNavigator by instance()
    private val notesListAdapterFactory: NotesListAdapterFactory by instance()
    private val viewModel: NotesListViewModel by activityViewModels {
        di.direct.instance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notesListAdapter = notesListAdapterFactory.create(parentFragmentManager,::onNoteClicked)
        viewModel.initializeNotes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.title = getString(R.string.notes_list_toolbar_title)
        notesRecyclerView = view.findViewById(R.id.notes_list)
        loadingIndicator = view.findViewById(R.id.loading_indicator)
        fab = view.findViewById(R.id.floating_action_button)

        fab.setOnClickListener {
            context?.let { screenNavigator.openAddNoteScreen(it) }
        }

        notesRecyclerView.apply {
            adapter = notesListAdapter
            notesRecyclerView.setHasFixedSize(true)
            notesRecyclerView.layoutManager = LinearLayoutManager(context)
            val spacing = resources.getDimension(R.dimen.note_item_spacing)
            addItemDecoration(SpacingItemDecoration(spacing.toInt()))
        }

        viewModel.viewState.observe(viewLifecycleOwner){
            render(it)
        }
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

    private fun onNoteClicked(note: Note) {
        val context = context ?: return
        screenNavigator.openEditNoteScreen(context, note.toParcelable())
    }
}
