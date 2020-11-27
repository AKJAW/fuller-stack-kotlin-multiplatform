package tests

import feature.socket.NoteSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import network.NoteSchema

class NoteSocketFake : NoteSocket {
    val notesMutableState: MutableStateFlow<List<NoteSchema>> = MutableStateFlow(listOf())
    var notes: List<NoteSchema>
        get() = notesMutableState.value
        set(value) {
            notesMutableState.value = value
        }

    override fun getNotesFlow(): Flow<List<NoteSchema>> = notesMutableState

    override fun close() { /* Empty */}
}
