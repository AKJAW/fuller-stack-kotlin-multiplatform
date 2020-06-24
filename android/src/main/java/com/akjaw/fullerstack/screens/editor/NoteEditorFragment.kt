package com.akjaw.fullerstack.screens.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseFragment
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import data.Note
import feature.noteslist.AddNote
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.launch
import org.kodein.di.instance

class NoteEditorFragment: BaseFragment(), NoteEditorViewMvc.Listener {

    private val addNote: AddNote by instance<AddNote>()
    private val noteInputValidator: NoteInputValidator by instance<NoteInputValidator>()
    private val screenNavigator: ScreenNavigator by instance<ScreenNavigator>()
    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private lateinit var viewMvc: NoteEditorViewMvc

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewMvc = viewMvcFactory.getNoteEditorViewMvc(container, R.menu.note_editor_add)

        viewMvc.setToolbarTitle(getString(R.string.note_editor_toolbar_title_add))
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

    override fun onActionClicked() {
        val isTitleValid = noteInputValidator.isTitleValid(viewMvc.getNoteTitle())
        if(isTitleValid) {
            val note = Note(viewMvc.getNoteTitle(), viewMvc.getNoteContent())
            addNote(note)
        } else {
            //TODO use the ValidationResult
            viewMvc.showNoteTitleError("Title is invalid")
        }
    }

    private fun addNote(note: Note) {
        lifecycleScope.launch {
            addNote.executeAsync(note) { result ->
                screenNavigator.goBack(requireContext())
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: Either<Failure, UseCaseAsync.None>) {
        when (result) {
            is Either.Left -> {
                //TODO the note should be flagged and a refresh icon should be shown
                Toast.makeText(
                    context,
                    getString(R.string.note_editor_failure_add),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Either.Right -> {
                Toast.makeText(
                    context,
                    getString(R.string.note_editor_success_add),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCancelClicked() {
        screenNavigator.goBack(requireContext())
    }

}