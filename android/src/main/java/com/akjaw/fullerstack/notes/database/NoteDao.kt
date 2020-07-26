package com.akjaw.fullerstack.notes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertNote(note: NoteEntity): Long //TODO When return is -1 then show a toast

    @Query(
        """
        UPDATE notes SET
        title = :title, 
        content = :content, 
        lastModificationTimestamp = :lastModificationTimestamp
        WHERE noteId = :noteId
        """
    )
    suspend fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        lastModificationTimestamp: Long
    )

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    suspend fun deleteNote(noteId: Int)
}
