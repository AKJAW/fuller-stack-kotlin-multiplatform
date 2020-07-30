package com.akjaw.fullerstack.screens.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akjaw.fullerstack.screens.common.LiveEvent
import com.akjaw.fullerstack.screens.common.ParcelableNote
import feature.NewAddNote
import feature.editor.UpdateNote
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.Note
import model.NoteIdentifier

class NoteEditorViewModel(
    private val applicationScope: CoroutineScope,
    private val addNote: NewAddNote,
    private val updateNote: UpdateNote,
    private val noteInputValidator: NoteInputValidator
) : ViewModel() {

    data class NoteEditorState(
        val note: ParcelableNote? = null,
        val titleError: String? = null
    )
    // TODO event for navigation
    private val _viewState = MutableLiveData<NoteEditorState>(NoteEditorState())
    val viewState: LiveData<NoteEditorState> = _viewState

    val navigationLiveEvent = LiveEvent<Unit>()

    fun setNote(note: ParcelableNote?) {
        _viewState.value = viewState.value?.copy(note = note)
    }

    fun onActionClicked(title: String, content: String) {
        if (noteInputValidator.isTitleValid(title).not()) {
            _viewState.value = viewState.value?.copy(titleError = "Title is invalid")
            return
        }

        val stateNote = viewState.value?.note
        val note = Note(title = title, content = content)

        if (stateNote?.id != null) {
            updateExistingNote(note.copy(noteIdentifier = NoteIdentifier(stateNote.id)))
        } else {
            addNewNote(note)
        }

        navigationLiveEvent.postValue(Unit)
    }

    private fun addNewNote(newNote: Note) = applicationScope.launch {
        val wasSuccessful = addNote.executeAsync(newNote)

        if (wasSuccessful.not()) {
            // TODO the note should be flagged and a refresh icon should be shown
            // TODO move to a separate class which can be use by both platforms
        }
    }

    private fun updateExistingNote(updatedNote: Note) = applicationScope.launch {
        require(updatedNote.noteIdentifier.id != -1)

        val wasSuccessful = updateNote.executeAsync(updatedNote)

        if (wasSuccessful.not()) {
            // TODO the note should be flagged and a refresh icon should be shown
            // TODO move to a separate class which can be use by both platforms
        }
    }
}
