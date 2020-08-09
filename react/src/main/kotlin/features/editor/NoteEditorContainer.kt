package features.editor

import composition.KodeinEntry
import helpers.validation.NoteInputValidator
import model.CreationTimestamp
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
    var addNote: (title: String, content: String) -> Unit
    var updateNote: (creationTimestamp: CreationTimestamp, title: String, content: String) -> Unit
    var deleteNotes: (creationTimestamps: List<CreationTimestamp>) -> Unit
    var closeEditor: () -> Unit
}

private interface StateProps : RProps {
    var selectedNote: Note?
    var isUpdating: Boolean
}

private interface DispatchProps : RProps {
    var addNote: (title: String, content: String) -> Unit
    var updateNote: (creationTimestamp: CreationTimestamp, title: String, content: String) -> Unit
    var deleteNotes: (creationTimestamps: List<CreationTimestamp>) -> Unit
    var closeEditor: () -> Unit
}

private class NoteEditorContainer(props: NoteEditorConnectedProps) :
    RComponent<NoteEditorConnectedProps, NoteEditorState>(props) {

    override fun NoteEditorState.init(props: NoteEditorConnectedProps) {
        isTitleValid = true
    }

    private val noteInputValidator: NoteInputValidator by KodeinEntry.di.instance()

    override fun RBuilder.render() {
        child(noteEditor) {
            attrs.key = props.selectedNote.toString()
            attrs.selectedNote = props.selectedNote
            attrs.isUpdating = props.isUpdating
            attrs.isTitleValid = state.isTitleValid
            attrs.positiveActionCaption = getPositiveActionCaption()
            attrs.onPositiveActionClicked = ::onPositiveActionClicked
            attrs.closeEditor = props.closeEditor
            attrs.onDeleteClicked = ::onDeleteClicked
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

        val selectedNote = props.selectedNote
        console.log(props.isUpdating)
        console.log(selectedNote)
        if (props.isUpdating && selectedNote != null) {
            props.updateNote(
                selectedNote.creationTimestamp,
                title,
                content
            )
        } else {
            props.addNote(title, content)
        }
    }

    private fun onDeleteClicked() {
        props.closeEditor()
        val noteIdentifier = props.selectedNote?.creationTimestamp
        if (noteIdentifier != null) {
            props.deleteNotes(listOf(noteIdentifier))
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
            addNote = { title: String, content: String -> dispatch(NoteEditorSlice.addNote(title, content)) }
            updateNote = { creationTimestamp: CreationTimestamp, title: String, content: String ->
                dispatch(NoteEditorSlice.updateNote(creationTimestamp, title, content))
            }
            closeEditor = { dispatch(NoteEditorSlice.CloseEditor()) }
            deleteNotes = { creationTimestamps: List<CreationTimestamp> ->
                dispatch(NoteEditorSlice.deleteNotes(creationTimestamps))
            }
        }
    )(NoteEditorContainer::class.js.unsafeCast<RClass<NoteEditorConnectedProps>>())
