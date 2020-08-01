package com.akjaw.fullerstack.notes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.akjaw.fullerstack.helpers.logger.log
import database.NoteDao
import database.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RoomNoteDao : NoteDao {

    @Query("SELECT * FROM notes ORDER BY creationTimestamp DESC")
    abstract override fun getAllNotes(): Flow<List<NoteEntity>>

    @Transaction
    override suspend fun addNote(note: NoteEntity): Int {
        val lastPrimaryId = getLastPrimaryId() ?: -1
        val noteId = lastPrimaryId + 1
        noteId.log("addNote")
        val noteWithCorrectId = note.copy(noteId = noteId)
        noteWithCorrectId.log("addNote")
        insertNote(noteWithCorrectId)

        return noteId
    }

    @Query("SELECT id FROM notes ORDER BY id DESC LIMIT 1")
    abstract suspend fun getLastPrimaryId(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNote(note: NoteEntity): Long

    @Query(
        """
        UPDATE notes SET
        title = :title, 
        content = :content, 
        lastModificationTimestamp = :lastModificationTimestamp
        WHERE noteId = :noteId
        """
    )//TODO maybe make it a private method which is called by the contract that takes in the entity
    abstract override suspend fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        lastModificationTimestamp: Long
    )

    @Query("UPDATE notes SET noteId = :apiId WHERE id = :localId")
    abstract override suspend fun updateId(localId: Int, apiId: Int)

    @Query("UPDATE notes SET hasSyncFailed = :hasSyncFailed WHERE noteId = :noteId")
    abstract override suspend fun updateSyncFailed(noteId: Int, hasSyncFailed: Boolean)

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    abstract override suspend fun deleteNote(noteId: Int)

    @Query("DELETE FROM notes WHERE noteId in (:noteIds)")
    abstract override suspend fun deleteNotes(noteIds: List<Int>)
}
