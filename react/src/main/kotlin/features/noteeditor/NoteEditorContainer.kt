package features.noteeditor

import data.Note
import react.RBuilder
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.child
import react.invoke
import react.key
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction
import store.AppState

interface NoteEditorConnectedProps : RProps {
    var selectedNote: Note?
    var addNote: (note: Note) -> Unit
}

private interface StateProps : RProps {
    var selectedNote: Note?
}

private interface DispatchProps : RProps {
    var addNote: (note: Note) -> Unit
}

private class NoteEditorContainer(props: NoteEditorConnectedProps) : RComponent<NoteEditorConnectedProps, RState>(props) {
    override fun RBuilder.render() {
        child(noteEditor) {
            attrs.key = props.selectedNote.toString()
            attrs.addNote = props.addNote
            attrs.selectedNote = props.selectedNote
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
        }
    )(NoteEditorContainer::class.js.unsafeCast<RClass<NoteEditorConnectedProps>>())
