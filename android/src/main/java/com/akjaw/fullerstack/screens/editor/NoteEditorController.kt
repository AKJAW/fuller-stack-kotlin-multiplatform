package com.akjaw.fullerstack.screens.editor

import android.widget.Toast
import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import data.Note
import feature.noteslist.AddNote
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NoteEditorController(
    private val addNote: AddNote,
    private val noteInputValidator: NoteInputValidator,
    private val screenNavigator: ScreenNavigator
): NoteEditorViewMvc.Listener {

    private lateinit var scope: CoroutineScope
    private lateinit var viewMvc: NoteEditorViewMvc

    fun bindView(viewMvc: NoteEditorViewMvc, scope: CoroutineScope) {
        this.viewMvc = viewMvc
        this.scope = scope

        viewMvc.setAddToolbarTitle()
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
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
        scope.launch {
            addNote.executeAsync(note) { result ->
                screenNavigator.goBack(viewMvc.rootView.context)
                handleResult(result)
            }
        }
    }

    private fun handleResult(result: Either<Failure, UseCaseAsync.None>) {
        val context = viewMvc.rootView.context
        when (result) {
            is Either.Left -> {
                //TODO the note should be flagged and a refresh icon should be shown
                //TODO move to a separate class which can be use by both platforms
                Toast.makeText(
                    context,
                    context.getString(R.string.note_editor_failure_add),
                    Toast.LENGTH_SHORT
                ).show()
            }
            is Either.Right -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.note_editor_success_add),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCancelClicked() {
        screenNavigator.goBack(viewMvc.rootView.context)
    }

}