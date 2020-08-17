package com.akjaw.fullerstack.notes.database

import androidx.room.TypeConverter
import model.CreationTimestamp
import model.LastModificationTimestamp
import model.toCreationTimestamp
import model.toLastModificationTimestamp

class TimestampConverter {

    @TypeConverter
    fun fromCreationTimestamp(value: Long): CreationTimestamp = value.toCreationTimestamp()

    @TypeConverter
    fun toCreationTimestamp(value: CreationTimestamp): Long = value.unix

    @TypeConverter
    fun fromLastModificationTimestamp(value: Long): LastModificationTimestamp = value.toLastModificationTimestamp()

    @TypeConverter
    fun toLastModificationTimestamp(value: LastModificationTimestamp): Long = value.unix
}