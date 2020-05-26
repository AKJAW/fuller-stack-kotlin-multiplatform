package features.noteslist

import data.Note
import dependencyinjection.KodeinEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.erased.instance
import react.RProps
import react.dom.div
import react.functionalComponent
import react.useEffect
import react.useState
import usecases.FetchNotesListUseCaseAsync

val notesList = functionalComponent<RProps> {
    val fetchNotesListUseCaseAsync by KodeinEntry.kodein.instance<FetchNotesListUseCaseAsync>()

    val (notes, setNotes) = useState(listOf<Note>(Note("0")))

    useEffect(listOf()) {
        GlobalScope.launch {
            val result = fetchNotesListUseCaseAsync.executeAsync(Dispatchers.Default)

            when(result){
                is FetchNotesListUseCaseAsync.FetchNotesListResult.Success -> setNotes(result.notes)
                FetchNotesListUseCaseAsync.FetchNotesListResult.Failure -> TODO()
            }
        }
    }

    div {
        notes.forEach { note ->
            div {
                + note.title
            }
        }
    }
}
