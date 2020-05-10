package com.akjaw.fullerstack.screens.notes_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import org.kodein.di.erased.instance
import sample.Counter

class NotesListFragment: BaseFragment(), NotesListViewMvc.Listener {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private val counter: Counter by instance<Counter>()
    private lateinit var viewMvc: NotesListViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNotesListViewMvc(container)

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

    override fun onButtonClicked() {
        val count = counter.getAndIncrement()

        viewMvc.setText(count.toString())
    }
}