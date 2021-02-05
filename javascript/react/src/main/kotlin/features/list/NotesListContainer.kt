package features.list

import feature.local.sort.SortProperty
import features.editor.NoteEditorSlice
import helpers.date.NoteDateFormat
import model.Note
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
    var searchValue: String
    var getNotesList: () -> Unit
    var synchronizeNotes: () -> Unit
    var openEditor: (note: Note?) -> Unit
    var changeSort: (sortProperty: SortProperty) -> Unit
    var changeSearchValue: (searchValue: String) -> Unit
}

private interface StateProps : RProps {
    var isLoading: Boolean
    var notesList: Array<Note>
    var sortProperty: SortProperty
    var searchValue: String
}

private interface DispatchProps : RProps {
    var getNotesList: (accessToken: String) -> Unit
    var synchronizeNotes: (accessToken: String) -> Unit
    var openEditor: (note: Note?) -> Unit
    var changeSort: (sortProperty: SortProperty) -> Unit
    var changeSearchValue: (searchValue: String) -> Unit
}

private class NotesListContainer(props: NotesListConnectedProps) : RComponent<NotesListConnectedProps, RState>(props) {
    val dateFormat = NoteDateFormat.Default

    override fun componentDidMount() {
        props.getNotesList()
        props.synchronizeNotes()
    }

    override fun RBuilder.render() {
        child(notesList) {
            attrs.isLoading = props.isLoading
            attrs.notesList = props.notesList
            attrs.sortProperty = props.sortProperty
            attrs.searchValue = props.searchValue
            attrs.dateFormat = dateFormat
            attrs.openEditor = props.openEditor
            attrs.changeSort = props.changeSort
            attrs.changeSearchValue = props.changeSearchValue
        }
    }
}

val notesListContainer: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, StateProps, DispatchProps, NotesListConnectedProps>(
        { state, _ ->
            notesList = state.notesListState.notesList
            isLoading = state.notesListState.isLoading
            sortProperty = state.notesListState.sortProperty
            searchValue = state.notesListState.searchValue
        },
        { dispatch, _ ->
            getNotesList = { dispatch(NotesListSlice.getNotesList()) }
            synchronizeNotes = { dispatch(NotesListSlice.synchronizeNotes()) }
            openEditor = { note -> dispatch(NoteEditorSlice.OpenEditor(note)) }
            changeSort = { sortProperty -> dispatch(NotesListSlice.SetSortProperty(sortProperty)) }
            changeSearchValue = { searchValue -> dispatch(NotesListSlice.SetSearchValue(searchValue)) }
        }
    )(NotesListContainer::class.js.unsafeCast<RClass<NotesListConnectedProps>>())
