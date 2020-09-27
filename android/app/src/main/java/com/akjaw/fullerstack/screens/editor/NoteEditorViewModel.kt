package com.akjaw.fullerstack.screens.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akjaw.fullerstack.screens.common.LiveEvent
import com.akjaw.fullerstack.screens.common.ParcelableNote
import feature.AddNote
import feature.UpdateNote
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import model.CreationTimestamp
import model.toCreationTimestamp

class NoteEditorViewModel(
    private val applicationScope: CoroutineScope,
    private val addNote: AddNote,
    private val updateNote: UpdateNote,
    private val noteInputValidator: NoteInputValidator
) : ViewModel() {

    data class NoteEditorState(
        val note: ParcelableNote? = null,
        val titleError: String? = null
    )

    private val _viewState = MutableLiveData<NoteEditorState>(NoteEditorState())
    val viewState: LiveData<NoteEditorState> = _viewState

    val navigationLiveEvent = LiveEvent<Unit>()

    fun setNote(note: ParcelableNote?) {
        _viewState.value = viewState.value?.copy(note = note)
    }

    fun onActionClicked(title: String, content: String) {
        val validationResult = noteInputValidator.isTitleValid(title)
        if (validationResult is NoteInputValidator.ValidationResult.Invalid) {
            _viewState.value = viewState.value?.copy(titleError = validationResult.errorMessage)
            return
        }

        val stateNote = viewState.value?.note

        if (stateNote?.creationUnixTimestamp != null) {
            updateExistingNote(
                creationTimestamp = stateNote.creationUnixTimestamp.toCreationTimestamp(),
                title = title,
                content = content
            )
        } else {
            addNewNote(title = title, content = content)
        }

        navigationLiveEvent.postValue(Unit)
    }

    private fun addNewNote(title: String, content: String) = applicationScope.launch {
        addNote.executeAsync(
            title = title,
            content = content
        )
    }

    private fun updateExistingNote(
        creationTimestamp: CreationTimestamp,
        title: String,
        content: String
    ) = applicationScope.launch {
        updateNote.executeAsync(
            creationTimestamp = creationTimestamp,
            title = title,
            content = content
        )
    }
}
