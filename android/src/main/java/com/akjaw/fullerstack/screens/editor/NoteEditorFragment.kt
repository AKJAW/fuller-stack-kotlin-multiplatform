package com.akjaw.fullerstack.screens.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import org.kodein.di.instance

class NoteEditorFragment: BaseFragment() {

    private val controller: NoteEditorController by instance<NoteEditorController>()
    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private lateinit var viewMvc: NoteEditorViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNoteEditorViewMvc(container, R.menu.note_editor_add)

        controller.bindView(viewMvc, lifecycleScope)

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }

}