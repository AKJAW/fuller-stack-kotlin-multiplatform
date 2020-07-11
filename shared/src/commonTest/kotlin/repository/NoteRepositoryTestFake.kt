package repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Note
import network.NoteApi
import network.NoteApiFake

class NoteRepositoryTestFake : NoteRepository {
    private val noteApi: NoteApi = NoteApiFake()
    private val notesMutableState: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())

    override suspend fun getNotes(): Flow<List<Note>> = notesMutableState

    override suspend fun refreshNotes() {
        val newNotes = noteApi.getNotes()
        notesMutableState.value = newNotes
    }

    override suspend fun addNote(note: Note) {
        val newNotes = notesMutableState.value + note
        notesMutableState.value = newNotes
    }

    override suspend fun updateNote(updatedNote: Note) {
        val newNotes = notesMutableState.value.map { note ->
            if(note.id == updatedNote.id) {
                note.copy(title = updatedNote.title, content = updatedNote.content)
            } else {
                note
            }
        }
        notesMutableState.value = newNotes
    }
}
