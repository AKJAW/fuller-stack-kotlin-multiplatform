package model

import com.soywiz.klock.DateTime

data class Note(
    val title: String = "",
    val content: String = "",
    val lastModificationTimestamp: LastModificationTimestamp = DateTime.nowUnixLong().toLastModificationTimestamp(),
    val creationTimestamp: CreationTimestamp = DateTime.nowUnixLong().toCreationTimestamp(),
    val hasSyncFailed: Boolean = false
)
