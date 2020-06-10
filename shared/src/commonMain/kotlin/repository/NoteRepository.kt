package repository

import data.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
interface NoteRepository {

    val notes: Flow<List<Note>>

    suspend fun refreshNotes()

    suspend fun addNote(note: Note)

}
