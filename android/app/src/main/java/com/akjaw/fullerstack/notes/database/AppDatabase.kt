package com.akjaw.fullerstack.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import database.NoteEntity

@Suppress("MagicNumber")
@Database(entities = [NoteEntity::class], version = 4, exportSchema = false)
@TypeConverters(TimestampConverter::class)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun create(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "fuller-stack"
            ).addMigrations(object : Migration(2, 3){
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE notes ADD wasDeleted INTEGER DEFAULT 0 NOT NULL")
                }
            }).build()
        }
    }

    abstract fun noteDao(): RoomNoteDao
}
