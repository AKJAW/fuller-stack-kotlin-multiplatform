package repository

import data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import network.NoteApi

//TODO how to handle errors
internal class NoteRepositoryFake(private val noteApi: NoteApi) : NoteRepository {
    private val notesMutableState: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    override val notes: Flow<List<Note>> = notesMutableState

    override suspend fun refreshNotes() {
        val newNotes = noteApi.getNotes()
        notesMutableState.value = newNotes
    }

    override suspend fun addNote(note: Note) {
        //It should add the note before the call to the api, but if there is an error. It should be updated with
        // a refresh icon
        val newNotes = notesMutableState.value + note
        notesMutableState.value = newNotes
    }

}
