package feature.socket

import kotlinx.coroutines.flow.Flow
import network.NoteSchema

interface NoteSocket {

    fun getNotesFlow(): Flow<List<NoteSchema>>

    fun close()
}
