package model

import kotlinx.serialization.Serializable

@Serializable
data class LastModificationTimestamp(val unix: Long)

fun Long.toLastModificationTimestamp(): LastModificationTimestamp = LastModificationTimestamp(this)
