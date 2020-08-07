package model

import kotlinx.serialization.Serializable

@Serializable
data class CreationTimestamp(val unix: Long)