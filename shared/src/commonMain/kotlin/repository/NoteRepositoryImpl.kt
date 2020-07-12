package repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Note
import network.NoteApi

class NoteRepositoryImpl(private val noteApi: NoteApi) : NoteRepository {
    private val notesMutableState: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())

    override suspend fun getNotes(): Flow<List<Note>> {
        if (notesMutableState.value.isEmpty()) { // TODO maybe a better check
            refreshNotes()
        }
        return notesMutableState
    }

    override suspend fun refreshNotes() {
        val newNotes = noteApi.getNotes()
        notesMutableState.value = newNotes
    }

    override suspend fun addNote(newNote: Note) {
        noteApi.addNote(newNote)
        refreshNotes()
    }

    override suspend fun updateNote(updatedNote: Note) {
        TODO("Not yet implemented")
    }
}