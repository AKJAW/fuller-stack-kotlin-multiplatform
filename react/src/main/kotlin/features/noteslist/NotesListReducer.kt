package features.noteslist

import data.Note
import redux.RAction

class SetNotesList(val notesList: Array<Note>): RAction

fun noteListReducer(state: Array<Note> = emptyArray(), action: RAction): Array<Note> {
    return when(action) {
        is SetNotesList -> {
            action.notesList
        }
        else -> state
    }
}