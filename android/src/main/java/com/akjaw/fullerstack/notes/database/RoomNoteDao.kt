package com.akjaw.fullerstack.notes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import database.NoteDao
import database.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomNoteDao : NoteDao {

    @Query("SELECT * FROM notes")
    override fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertNote(note: NoteEntity): Long //TODO When return is -1 then show a toast

    @Query(
        """
        UPDATE notes SET
        title = :title, 
        content = :content, 
        lastModificationTimestamp = :lastModificationTimestamp
        WHERE noteId = :noteId
        """
    )

    //TODO maybe make it a private method which is called by the contract that takes in the entity
    override suspend fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        lastModificationTimestamp: Long
    )

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    override suspend fun deleteNote(noteId: Int)
}
