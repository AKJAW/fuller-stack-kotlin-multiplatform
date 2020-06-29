package repository

import data.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface NoteRepository {

    suspend fun getNotes(): Flow<List<Note>>

    suspend fun refreshNotes()

    suspend fun addNote(note: Note)

    suspend fun updateNote(note: Note)

}
