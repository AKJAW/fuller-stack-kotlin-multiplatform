package com.akjaw.fullerstack.notes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import database.NoteDao
import database.NoteEntity
import feature.AddNotePayload
import feature.UpdateNotePayload
import kotlinx.coroutines.flow.Flow
import model.LastModificationTimestamp

@Dao
abstract class RoomNoteDao : NoteDao {

    @Query("SELECT * FROM notes ORDER BY creationTimestamp DESC")
    abstract override fun getAllNotes(): Flow<List<NoteEntity>>

    @Transaction
    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        val lastPrimaryId = getLastPrimaryId() ?: -1
        val noteId = lastPrimaryId + 1
        val note = NoteEntity(
            noteId = noteId,
            title = addNotePayload.title,
            content = addNotePayload.content,
            lastModificationTimestamp = addNotePayload.lastModificationTimestamp,
            creationTimestamp = addNotePayload.creationTimestamp
        )
        insertNote(note)

        return noteId
    }

    @Query("SELECT id FROM notes ORDER BY id DESC LIMIT 1")
    abstract suspend fun getLastPrimaryId(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNote(note: NoteEntity): Long

    @Transaction
    override suspend fun updateNote(updateNotePayload: UpdateNotePayload) {
        updateNote(
            noteId = updateNotePayload.noteId,
            title = updateNotePayload.title,
            content = updateNotePayload.content,
            lastModificationTimestamp = updateNotePayload.lastModificationTimestamp
        )
    }

    @Query(
        """
        UPDATE notes SET
        title = :title, 
        content = :content, 
        lastModificationTimestamp = :lastModificationTimestamp
        WHERE noteId = :noteId
        """
    )
    abstract suspend fun updateNote(
        noteId: Int,
        title: String,
        content: String,
        lastModificationTimestamp: LastModificationTimestamp
    )

    @Query("UPDATE notes SET noteId = :apiId WHERE id = :localId")
    abstract override suspend fun updateNoteId(localId: Int, apiId: Int)

    @Query("UPDATE notes SET hasSyncFailed = :hasSyncFailed WHERE noteId = :noteId")
    abstract override suspend fun updateSyncFailed(noteId: Int, hasSyncFailed: Boolean)

    @Query("DELETE FROM notes WHERE noteId = :noteId")
    abstract override suspend fun deleteNote(noteId: Int)

    @Query("DELETE FROM notes WHERE noteId in (:noteIds)")
    abstract override suspend fun deleteNotes(noteIds: List<Int>)

    @Query("UPDATE notes SET wasDeleted = :wasDeleted WHERE noteId in (:noteIds)")
    abstract override suspend fun setWasDeleted(
        noteIds: List<Int>,
        wasDeleted: Boolean
    )
}
