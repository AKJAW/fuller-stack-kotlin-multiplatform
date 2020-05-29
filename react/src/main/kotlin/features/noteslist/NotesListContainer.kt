package features.noteslist

import data.Note
import dependencyinjection.KodeinEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance
import react.*
import react.redux.rConnect
import redux.WrapperAction
import store.State
import usecases.FetchNotesListUseCaseAsync

interface NotesListConnectedProps : RProps {
    var notesList: Array<Note>
    var setNotesList: (Array<Note>) -> Unit
}

private interface StateProps : RProps {
    var notesList: Array<Note>
}

private interface DispatchProps : RProps {
    var setNotesList: (Array<Note>) -> Unit
}

private class NotesListContainer(props: NotesListConnectedProps) : RComponent<NotesListConnectedProps, RState>(props) {
    val fetchNotesListUseCaseAsync by KodeinEntry.kodein.instance<FetchNotesListUseCaseAsync>()

    override fun componentDidMount() {
        GlobalScope.launch {
            val result = fetchNotesListUseCaseAsync.executeAsync(Dispatchers.Default)

            when(result){
                is FetchNotesListUseCaseAsync.FetchNotesListResult.Success -> {
                    props.setNotesList(result.notes.toTypedArray())
                }
                FetchNotesListUseCaseAsync.FetchNotesListResult.Failure -> TODO()
            }
        }
    }

    override fun RBuilder.render() {
        child(notesList) {
            attrs.notesList = props.notesList
        }
    }
}

val notesListContainer: RClass<RProps> =
    rConnect<State, SetNotesList, WrapperAction, RProps, StateProps, DispatchProps, NotesListConnectedProps>(
        { state, _ ->
            notesList = state.noteList
        },
        { dispatch, _ ->
            setNotesList = { notes -> dispatch(SetNotesList(notes)) }
        }
    )(NotesListContainer::class.js.unsafeCast<RClass<NotesListConnectedProps>>())
