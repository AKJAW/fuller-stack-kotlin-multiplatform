package com.akjaw.fullerstack.screens.editor

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.helpers.viewmodel.viewModels
import com.akjaw.fullerstack.screens.common.ParcelableNote
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import com.google.android.material.textfield.TextInputLayout
import org.kodein.di.instance

class NoteEditorFragment : BaseFragment(R.layout.layout_note_editor) {

    companion object {
        private const val NOTE_EXTRA = "NOTE_EXTRA"
        fun newInstance(note: ParcelableNote?): NoteEditorFragment {
            val args = Bundle()
            args.putParcelable(NOTE_EXTRA, note)

            return NoteEditorFragment().apply {
                arguments = args
            }
        }
    }

    private val screenNavigator: ScreenNavigator by instance()
    private val noteEditorViewModel: NoteEditorViewModel by viewModels()

    private lateinit var toolbar: Toolbar
    private lateinit var titleLayout: TextInputLayout
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val note = getNote()
        noteEditorViewModel.setNote(note)

        noteEditorViewModel.navigationLiveEvent.observe(this) {
            screenNavigator.goBack(requireContext())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar = view.findViewById(R.id.toolbar)
        titleLayout = view.findViewById(R.id.title_layout)
        titleEditText = view.findViewById(R.id.title_edit_text)
        contentEditText = view.findViewById(R.id.content_edit_text)

        setUpToolbar()

        noteEditorViewModel.viewState.observe(viewLifecycleOwner, Observer {
            render(it)
        })
    }

    private fun setUpToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_close_24dp)
        toolbar.setNavigationOnClickListener {
            hideKeyboard()
            screenNavigator.goBack(requireContext())
        }
        toolbar.setOnMenuItemClickListener {
            hideKeyboard()
            noteEditorViewModel.onActionClicked(titleEditText.text.toString(), contentEditText.text.toString())
            true
        }
        if (getNote() != null) {
            toolbar.title = requireContext().getString(R.string.note_editor_toolbar_title_update)
            toolbar.inflateMenu(R.menu.note_editor_update)

        } else {
            toolbar.title = requireContext().getString(R.string.note_editor_toolbar_title_add)
            toolbar.inflateMenu(R.menu.note_editor_add)
        }
    }

    private fun getNote(): ParcelableNote? = arguments?.getParcelable(NOTE_EXTRA)

    private fun render(noteEditorState: NoteEditorViewModel.NoteEditorState) {
        titleLayout.error = noteEditorState.titleError ?: ""

        if(noteEditorState.note != null){
            titleEditText.setText(noteEditorState.note.title)
            contentEditText.setText(noteEditorState.note.content)
        }
    }
}
