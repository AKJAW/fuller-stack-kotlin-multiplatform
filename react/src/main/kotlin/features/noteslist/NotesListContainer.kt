package features.noteslist

import data.Note
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
import store.State

interface NotesListConnectedProps : RProps {
    var notesList: Array<Note>
    var fetchNotesList: () -> Unit
    var addNote: (note: Note) -> Unit
}

private interface StateProps : RProps {
    var notesList: Array<Note>
}

private interface DispatchProps : RProps {
    var fetchNotesList: () -> Unit
    var addNote: (note: Note) -> Unit
}

private class NotesListContainer(props: NotesListConnectedProps) : RComponent<NotesListConnectedProps, RState>(props) {

    override fun componentDidMount() {
        props.fetchNotesList()
    }

    override fun RBuilder.render() {
        child(notesList) {
            attrs.notesList = props.notesList
            attrs.onNoteClicked = props.addNote
        }
    }
}

val notesListContainer: RClass<RProps> =
    rConnect<State, RAction, WrapperAction, RProps, StateProps, DispatchProps, NotesListConnectedProps>(
        { state, _ ->
            notesList = state.noteList
        },
        { dispatch, _ ->
            fetchNotesList = { dispatch(NotesListSlice.fetchNotesList()) }
            addNote = { note -> dispatch(NotesListSlice.addNote(note)) }
        }
    )(NotesListContainer::class.js.unsafeCast<RClass<NotesListConnectedProps>>())
