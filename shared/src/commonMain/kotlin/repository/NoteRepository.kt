package repository

import kotlinx.coroutines.flow.Flow
import model.Note
import model.NoteIdentifier

interface NoteRepository {

    suspend fun getNotes(): Flow<List<Note>>

    suspend fun refreshNotes()

    suspend fun addNote(newNote: Note)

    suspend fun updateNote(updatedNote: Note)

    suspend fun deleteNotes(noteIdentifiers: List<NoteIdentifier>)
}
