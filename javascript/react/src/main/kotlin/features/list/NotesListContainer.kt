package features.list

import composition.KodeinEntry
import feature.local.sort.SortProperty
import features.editor.NoteEditorSlice
import helpers.date.PatternProvider
import model.Note
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
    var sortProperty: SortProperty
    var getNotesList: () -> Unit
    var synchronizeNotes: () -> Unit
    var openEditor: (note: Note?) -> Unit
    var changeSort: (sortProperty: SortProperty) -> Unit
}

private interface StateProps : RProps {
    var isLoading: Boolean
    var notesList: Array<Note>
    var sortProperty: SortProperty
}

private interface DispatchProps : RProps {
    var getNotesList: (accessToken: String) -> Unit
    var synchronizeNotes: (accessToken: String) -> Unit
    var openEditor: (note: Note?) -> Unit
    var changeSort: (sortProperty: SortProperty) -> Unit
}

private class NotesListContainer(props: NotesListConnectedProps) : RComponent<NotesListConnectedProps, RState>(props) {
    val patternProvider by KodeinEntry.di.instance<PatternProvider>()
    val dateFormat = patternProvider.getNotesListItemPattern()

    override fun componentDidMount() {
        props.getNotesList()
        props.synchronizeNotes()
    }

    override fun RBuilder.render() {
        child(notesList) {
            attrs.isLoading = props.isLoading
            attrs.notesList = props.notesList
            attrs.sortProperty = props.sortProperty
            attrs.dateFormat = dateFormat
            attrs.openEditor = props.openEditor
            attrs.changeSort = props.changeSort
        }
    }
}

val notesListContainer: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, StateProps, DispatchProps, NotesListConnectedProps>(
        { state, _ ->
            notesList = state.notesListState.notesList
            isLoading = state.notesListState.isLoading
            sortProperty = state.notesListState.sortProperty
        },
        { dispatch, _ ->
            getNotesList = { dispatch(NotesListSlice.getNotesList()) }
            synchronizeNotes = { dispatch(NotesListSlice.synchronizeNotes()) }
            openEditor = { note -> dispatch(NoteEditorSlice.OpenEditor(note)) }
            changeSort = { sortProperty -> dispatch(NotesListSlice.SetSortProperty(sortProperty)) }
        }
    )(NotesListContainer::class.js.unsafeCast<RClass<NotesListConnectedProps>>())
