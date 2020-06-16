package features.noteeditor

import data.Note
import features.noteslist.NotesListSlice
import react.RBuilder
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.child
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction
import store.AppState

interface NoteEditorConnectedProps : RProps {
    var addNote: (note: Note) -> Unit
}

private interface StateProps : RProps {
}

private interface DispatchProps : RProps {
    var addNote: (note: Note) -> Unit
}

private class NoteEditorContainer(props: NoteEditorConnectedProps) : RComponent<NoteEditorConnectedProps, RState>(props) {
    override fun RBuilder.render() {
        child(noteEditor) {
            attrs.addNote = props.addNote
        }
    }
}

val noteEditorContainer: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, StateProps, DispatchProps, NoteEditorConnectedProps>(
        { state, _ ->

        },
        { dispatch, _ ->
            addNote = { note -> dispatch(NotesListSlice.addNote(note)) }
        }
    )(NoteEditorContainer::class.js.unsafeCast<RClass<NoteEditorConnectedProps>>())
