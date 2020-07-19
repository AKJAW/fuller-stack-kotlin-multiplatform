package features.editor

import composition.KodeinEntry
import helpers.validation.NoteInputValidator
import model.Note
import org.kodein.di.instance
import react.RBuilder
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.child
import react.invoke
import react.key
import react.redux.rConnect
import react.setState
import redux.RAction
import redux.WrapperAction
import store.AppState

private interface NoteEditorState : RState {
    var isTitleValid: Boolean
}

interface NoteEditorConnectedProps : RProps {
    var selectedNote: Note?
    var isUpdating: Boolean
    var addNote: (note: Note) -> Unit
    var updateNote: (note: Note) -> Unit
    var closeEditor: () -> Unit
}

private interface StateProps : RProps {
    var selectedNote: Note?
    var isUpdating: Boolean
}

private interface DispatchProps : RProps {
    var addNote: (note: Note) -> Unit
    var updateNote: (note: Note) -> Unit
    var closeEditor: () -> Unit
}

private class NoteEditorContainer(props: NoteEditorConnectedProps) :
    RComponent<NoteEditorConnectedProps, NoteEditorState>(props) {

    override fun NoteEditorState.init(props: NoteEditorConnectedProps) {
        isTitleValid = true
    }

    private val noteInputValidator: NoteInputValidator by KodeinEntry.di.instance<NoteInputValidator>()

    override fun RBuilder.render() {
        child(noteEditor) {
            attrs.key = props.selectedNote.toString()
            attrs.selectedNote = props.selectedNote
            attrs.isUpdating = props.isUpdating
            attrs.isTitleValid = state.isTitleValid
            attrs.positiveActionCaption = getPositiveActionCaption()
            attrs.onPositiveActionClicked = ::onPositiveActionClicked
            attrs.closeEditor = props.closeEditor
        }
    }

    fun getPositiveActionCaption(): String =
        when {
            props.isUpdating -> "Update"
            else -> "Add"
        }

    fun onPositiveActionClicked(title: String, content: String) {
        val isTitleValid = noteInputValidator.isTitleValid(title)
        if (!isTitleValid) {
            setState { this.isTitleValid = false }
            return
        }
        setState { this.isTitleValid = true }

        val newNote = Note(title = title, content = content)
        val selectedNote = props.selectedNote
        console.log(props.isUpdating)
        console.log(selectedNote)
        if (props.isUpdating && selectedNote != null) {
            val noteWithId = newNote.copy(noteIdentifier = selectedNote.noteIdentifier)
            props.updateNote(noteWithId)
        } else {
            props.addNote(newNote)
        }
    }
}

val noteEditorContainer: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, StateProps, DispatchProps, NoteEditorConnectedProps>(
        { state, _ ->
            selectedNote = state.noteEditorState.selectedNote
            isUpdating = state.noteEditorState.isUpdating
        },
        { dispatch, _ ->
            addNote = { note -> dispatch(NoteEditorSlice.addNote(note)) }
            updateNote = { note -> dispatch(NoteEditorSlice.updateNote(note)) }
            closeEditor = { dispatch(NoteEditorSlice.CloseEditor()) }
        }
    )(NoteEditorContainer::class.js.unsafeCast<RClass<NoteEditorConnectedProps>>())
