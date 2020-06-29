package features.noteeditor

import data.Note
import dependencyinjection.KodeinEntry
import helpers.validation.NoteInputValidator
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

private interface NoteEditorState: RState {
    var isTitleValid: Boolean
}

interface NoteEditorConnectedProps : RProps {
    var selectedNote: Note?
    var addNote: (note: Note) -> Unit
    var closeEditor: () -> Unit
}

private interface StateProps : RProps {
    var selectedNote: Note?
}

private interface DispatchProps : RProps {
    var addNote: (note: Note) -> Unit
    var closeEditor: () -> Unit
}

private class NoteEditorContainer(props: NoteEditorConnectedProps)
    : RComponent<NoteEditorConnectedProps, NoteEditorState>(props) {

    override fun NoteEditorState.init(props: NoteEditorConnectedProps) {
        isTitleValid = true
    }

    private val noteInputValidator: NoteInputValidator by KodeinEntry.di.instance<NoteInputValidator>()

    fun validateAndAddNote(title: String, content: String){
        if(noteInputValidator.isTitleValid(title)){
            props.addNote(Note(id = -1, title = title, content = content))
            setState { isTitleValid = true }
        } else {//TODO use the ValidationResult
            setState { isTitleValid = false }
        }

    }

    override fun RBuilder.render() {
        child(noteEditor) {
            attrs.key = props.selectedNote.toString()
            attrs.selectedNote = props.selectedNote
            attrs.isTitleValid = state.isTitleValid
            attrs.validateAndAddNote = ::validateAndAddNote
            attrs.closeEditor = props.closeEditor
        }
    }
}

val noteEditorContainer: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, StateProps, DispatchProps, NoteEditorConnectedProps>(
        { state, _ ->
            selectedNote = state.noteEditorState.selectedNote
        },
        { dispatch, _ ->
            addNote = { note -> dispatch(NoteEditorSlice.addNote(note)) }
            closeEditor = { dispatch(NoteEditorSlice.CloseEditor()) }
        }
    )(NoteEditorContainer::class.js.unsafeCast<RClass<NoteEditorConnectedProps>>())
