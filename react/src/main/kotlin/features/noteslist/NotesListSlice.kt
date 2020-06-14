package features.noteslist

import data.Note
import features.noteslist.thunk.AddNoteThunk
import features.noteslist.thunk.FetchNotesListThunk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import redux.RAction
import store.RThunk

object NotesListSlice {
    private val notesListScope = CoroutineScope(SupervisorJob())
    private val fetchNotesListThunk = FetchNotesListThunk(notesListScope)

    fun fetchNotesList(): RThunk = fetchNotesListThunk

    fun addNote(note: Note): RThunk = AddNoteThunk(notesListScope, note)

    class SetNotesList(val notesList: Array<Note>) : RAction

    fun reducer(state: Array<Note> = emptyArray(), action: RAction): Array<Note> {
        return when (action) {
            is SetNotesList -> {
                action.notesList
            }
            else -> state
        }
    }
}
