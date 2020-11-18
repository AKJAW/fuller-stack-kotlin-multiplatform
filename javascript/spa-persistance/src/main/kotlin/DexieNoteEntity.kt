data class DexieNoteEntity(
    val localId: Int? = undefined,
    val title: String,
    val content: String,
    val lastModificationTimestamp: String,
    val creationTimestamp: String,
    val hasSyncFailed: Boolean = false,
    val wasDeleted: Boolean = false
)
