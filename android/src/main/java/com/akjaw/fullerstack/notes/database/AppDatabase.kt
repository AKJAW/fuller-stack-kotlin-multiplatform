package com.akjaw.fullerstack.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.NoteEntity

@Database(entities = [NoteEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): RoomNoteDao
}
