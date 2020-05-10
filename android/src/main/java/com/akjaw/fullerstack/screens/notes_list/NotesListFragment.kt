package com.akjaw.fullerstack.screens.notes_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NotesListFragment: Fragment(), NotesListViewMvc.Listener {

    private lateinit var viewMvc: NotesListViewMvc
    private var count = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = NotesListViewMvc(layoutInflater, container)

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
        count++
        viewMvc.setText(count.toString())
    }
}