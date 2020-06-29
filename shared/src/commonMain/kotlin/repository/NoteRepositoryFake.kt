package repository

import data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import network.NoteApi

//TODO how to handle errors
internal class NoteRepositoryFake(private val noteApi: NoteApi) : NoteRepository {
    private val notesMutableState: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())

    override suspend fun getNotes(): Flow<List<Note>> {
        if(notesMutableState.value.isEmpty()){ //TODO maybe a better check
            refreshNotes()
        }
        return notesMutableState
    }

    override suspend fun refreshNotes() {
        val newNotes = noteApi.getNotes()
        notesMutableState.value = newNotes
    }

    override suspend fun addNote(newNote: Note) {
        // This should add the note before the call to the api, but if there is an error. It should be updated with
        // a refresh icon
        val latestId= notesMutableState.value.maxBy { it.id }?.id ?: -1
        val noteWithId = newNote.copy(id = latestId + 1)

        val newNotes = notesMutableState.value + noteWithId
        notesMutableState.value = newNotes
    }

    override suspend fun updateNote(updatedNote: Note) {
        notesMutableState.value = notesMutableState.value.map {  note ->
            if (note.id == updatedNote.id){
                note.copy(
                    title = updatedNote.title,
                    content = updatedNote.content
                )
            } else {
                note
            }
        }
    }

}
