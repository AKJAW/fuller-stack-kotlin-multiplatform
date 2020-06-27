package repository

import data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import network.NoteApi

class NoteRepositoryTestFake(private val noteApi: NoteApi) : NoteRepository {
    private val notesMutableState: MutableStateFlow<List<Note>> = MutableStateFlow(listOf())
    override val notes: Flow<List<Note>> = notesMutableState

    var refreshNotesCallCount = 0
    var addNoteCallCount = 0
    val addedNotes = mutableListOf<Note>()

    override suspend fun refreshNotes() {
        refreshNotesCallCount++
        val newNotes = noteApi.getNotes()
        notesMutableState.value = newNotes
    }

    override suspend fun addNote(note: Note) {
        addNoteCallCount++
        addedNotes.add(note)
        val newNotes = notesMutableState.value + note
        notesMutableState.value = newNotes
    }

}
