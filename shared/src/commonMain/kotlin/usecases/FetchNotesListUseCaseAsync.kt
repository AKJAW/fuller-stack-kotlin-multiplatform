package usecases

import data.Note
import kotlinx.coroutines.delay

// TODO refactor
class FetchNotesListUseCaseAsync {

    interface Listener {
        fun onNoteListFetchSuccess(notes: List<Note>)
        fun onNoteListFetchFail()
    }

    private val listeners = mutableListOf<Listener>()

    fun registerListener(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListener(listener: Listener) {
        listeners.remove(listener)
    }

    suspend fun run() {
        delay(500)
        val notes = List(10) { Note(it.toString()) }
        listeners.forEach {
            it.onNoteListFetchSuccess(notes)
        }
    }
}
