package com.akjaw.fullerstack.screens.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import org.kodein.di.instance

class NotesListFragment : BaseFragment() {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private val notesListController: NotesListController by instance<NotesListController>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("NotesListFragment", "onCreateView")
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)
        notesListController.bindView(viewMvc, lifecycleScope)

        return viewMvc.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("NotesListFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        Log.d("NotesListFragment", "onDestroy")
        super.onDestroy()
    }

    override fun onStart() {
        Log.d("NotesListFragment", "onStart")
        super.onStart()
        notesListController.onStart()
    }

    override fun onStop() {
        Log.d("NotesListFragment", "onStop")
        super.onStop()
        notesListController.onStop()
    }
}
