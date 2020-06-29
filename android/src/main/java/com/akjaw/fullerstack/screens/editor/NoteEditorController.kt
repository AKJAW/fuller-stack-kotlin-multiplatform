package com.akjaw.fullerstack.screens.editor

import android.widget.Toast
import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ParcelableNote
import com.akjaw.fullerstack.screens.common.navigation.ScreenNavigator
import data.Note
import feature.noteslist.AddNote
import feature.noteslist.UpdateNote
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class NoteEditorController(
    private val addNote: AddNote,
    private val updateNote: UpdateNote,
    private val noteInputValidator: NoteInputValidator,
    private val screenNavigator: ScreenNavigator
): NoteEditorViewMvc.Listener {

    private lateinit var scope: CoroutineScope
    private lateinit var viewMvc: NoteEditorViewMvc
    private var note: ParcelableNote? = null

    fun bindView(
        viewMvc: NoteEditorViewMvc,
        scope: CoroutineScope,
        note: ParcelableNote?
    ) {
        this.viewMvc = viewMvc
        this.scope = scope

        this.note = note

        if(note != null){
            viewMvc.setNoteTitle(note.title)
            viewMvc.setNoteContent(note.content)
            viewMvc.setUpdateToolbarTitle()
        } else {
            viewMvc.setAddToolbarTitle()
        }
    }

    fun onStart() {
        viewMvc.registerListener(this)
    }

    fun onStop() {
        viewMvc.unregisterListener(this)
    }

    override fun onActionClicked() {
        val isTitleValid = noteInputValidator.isTitleValid(viewMvc.getNoteTitle())
        if(!isTitleValid) {
            //TODO use the ValidationResult
            viewMvc.showNoteTitleError("Title is invalid")
            return
        }

        viewMvc.hideKeyboard()

        val note = note
        if(note == null){
            addNewNote()
        } else {
            updateExistingNote(note.id)
        }
    }

    private fun addNewNote() {
        val note = Note(
            title = viewMvc.getNoteTitle(),
            content = viewMvc.getNoteContent()
        )
        scope.launch {
            addNote.executeAsync(note, ::handleResult)
        }
    }

    private fun updateExistingNote(id: Int){
        val note = Note(
            id = id,
            title = viewMvc.getNoteTitle(),
            content = viewMvc.getNoteContent()
        )
        scope.launch {
            updateNote.executeAsync(note, ::handleResult)
        }
    }

    private fun handleResult(result: Either<Failure, UseCaseAsync.None>) {
        screenNavigator.goBack(viewMvc.rootView.context)

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
        viewMvc.hideKeyboard()
        screenNavigator.goBack(viewMvc.rootView.context)
    }

}
