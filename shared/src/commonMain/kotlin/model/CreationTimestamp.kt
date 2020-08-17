package model

import kotlinx.serialization.Serializable

@Serializable
data class CreationTimestamp(val unix: Long)

fun Long.toCreationTimestamp(): CreationTimestamp = CreationTimestamp(this)
