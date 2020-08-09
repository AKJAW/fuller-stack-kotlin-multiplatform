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
import model.CreationTimestamp
import model.LastModificationTimestamp

@Dao
abstract class RoomNoteDao : NoteDao {

    @Query("SELECT * FROM notes ORDER BY creationTimestamp DESC")
    abstract override fun getAllNotes(): Flow<List<NoteEntity>>

    @Transaction
    override suspend fun addNote(addNotePayload: AddNotePayload): Int {
        val note = NoteEntity(
            title = addNotePayload.title,
            content = addNotePayload.content,
            lastModificationTimestamp = addNotePayload.lastModificationTimestamp,
            creationTimestamp = addNotePayload.creationTimestamp
        )

        return insertNote(note).toInt()
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertNote(note: NoteEntity): Long

    @Transaction
    override suspend fun updateNote(updateNotePayload: UpdateNotePayload) {
        updateNote(
            title = updateNotePayload.title,
            content = updateNotePayload.content,
            lastModificationTimestamp = updateNotePayload.lastModificationTimestamp,
            creationTimestamp = updateNotePayload.creationTimestamp
        )
    }

    @Query(
        """
        UPDATE notes SET
        title = :title, 
        content = :content, 
        lastModificationTimestamp = :lastModificationTimestamp
        WHERE creationTimestamp = :creationTimestamp
        """
    )
    abstract suspend fun updateNote(
        title: String,
        content: String,
        lastModificationTimestamp: LastModificationTimestamp,
        creationTimestamp: CreationTimestamp
    )

    @Query("UPDATE notes SET hasSyncFailed = :hasSyncFailed WHERE creationTimestamp = :creationTimestamp")
    abstract override suspend fun updateSyncFailed(creationTimestamp: CreationTimestamp, hasSyncFailed: Boolean)

    @Query("DELETE FROM notes WHERE creationTimestamp in (:creationTimestamps)")
    abstract override suspend fun deleteNotes(creationTimestamps: List<CreationTimestamp>)

    @Query("UPDATE notes SET wasDeleted = :wasDeleted WHERE creationTimestamp in (:creationTimestamps)")
    abstract override suspend fun setWasDeleted(
        creationTimestamps: List<CreationTimestamp>,
        wasDeleted: Boolean
    )
}
