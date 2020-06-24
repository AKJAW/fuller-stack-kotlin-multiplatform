package com.akjaw.fullerstack.screens.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.common.navigation.NotesListScreen
import org.kodein.di.instance

class NotesListFragment : BaseFragment() {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private val notesListController: NotesListController by instance<NotesListController>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)
        notesListController.bindView(viewMvc, lifecycleScope)
        val key: NotesListScreen = getKey()
        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        notesListController.onStart()
    }

    override fun onStop() {
        super.onStop()
        notesListController.onStop()
    }
}
