package features.noteslist

import data.Note
import dependencyinjection.KodeinEntry
import features.noteeditor.NoteEditorSlice
import helpers.date.PatternProvider
import org.kodein.di.instance
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

interface NotesListConnectedProps : RProps {
    var isLoading: Boolean
    var notesList: Array<Note>
    var fetchNotesList: () -> Unit
    var selectNote: (note: Note) -> Unit
}

private interface StateProps : RProps {
    var isLoading: Boolean
    var notesList: Array<Note>
}

private interface DispatchProps : RProps {
    var fetchNotesList: () -> Unit
    var selectNote: (note: Note) -> Unit
}

private class NotesListContainer(props: NotesListConnectedProps) : RComponent<NotesListConnectedProps, RState>(props) {
    val patternProvider by KodeinEntry.di.instance<PatternProvider>()
    val dateFormat = patternProvider.getNotesListItemPattern()

    override fun componentDidMount() {
        props.fetchNotesList()
    }

    override fun RBuilder.render() {
        child(notesList) {
            attrs.isLoading = props.isLoading
            attrs.notesList = props.notesList
            attrs.dateFormat = dateFormat
            attrs.onNoteClicked = props.selectNote
        }
    }
}

val notesListContainer: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, StateProps, DispatchProps, NotesListConnectedProps>(
        { state, _ ->
            notesList = state.notesListState.notesList
            isLoading = state.notesListState.isLoading
        },
        { dispatch, _ ->
            fetchNotesList = { dispatch(NotesListSlice.fetchNotesList()) }
            selectNote = { note -> dispatch(NoteEditorSlice.SelectNote(note)) }
        }
    )(NotesListContainer::class.js.unsafeCast<RClass<NotesListConnectedProps>>())
