package tests

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Note
import model.NoteIdentifier
import network.NoteApi
import repository.NoteRepository

@Suppress("TooGenericExceptionThrown")
class NoteRepositoryTestFake : NoteRepository {
    private val noteApi: NoteApi = NetworkApiTestFake() //TODO use this
    private val notesMutableState: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    val notesList: List<Note>
        get() = notesMutableState.value

    private var shouldNoteFlowThrow = false

    override suspend fun getNotes(): Flow<List<Note>> {
        return if(shouldNoteFlowThrow){
            throw RuntimeException() //TODO
        } else {
            notesMutableState
        }
    }

    override suspend fun refreshNotes() {
        val newNotes = noteApi.getNotes()
        notesMutableState.value = newNotes
    }

    override suspend fun addNote(newNote: Note) {
        val newNotes = notesMutableState.value + newNote
        notesMutableState.value = newNotes
    }

    override suspend fun updateNote(updatedNote: Note) {
        val newNotes = notesMutableState.value.map { note ->
            if(note.noteIdentifier == updatedNote.noteIdentifier) {
                note.copy(title = updatedNote.title, content = updatedNote.content)
            } else {
                note
            }
        }
        notesMutableState.value = newNotes
    }

    override suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>) {
        val newNotes = notesMutableState.value.filterNot { noteIdentifiers.contains(it.noteIdentifier) }
        notesMutableState.value = newNotes
    }

    fun setNotes(notes: List<Note>) {
        notesMutableState.value = notes
    }

    fun setNotesFlowError() {
        shouldNoteFlowThrow = true
    }
}
