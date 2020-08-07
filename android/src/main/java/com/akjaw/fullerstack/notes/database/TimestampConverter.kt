package com.akjaw.fullerstack.notes.database

import androidx.room.TypeConverter
import model.CreationTimestamp
import model.LastModificationTimestamp

class TimestampConverter {

    @TypeConverter
    fun fromCreationTimestamp(value: Long): CreationTimestamp = CreationTimestamp(value)

    @TypeConverter
    fun toCreationTimestamp(value: CreationTimestamp): Long = value.unix

    @TypeConverter
    fun fromLastModificationTimestamp(value: Long): LastModificationTimestamp = LastModificationTimestamp(value)

    @TypeConverter
    fun toLastModificationTimestamp(value: LastModificationTimestamp): Long = value.unix
}