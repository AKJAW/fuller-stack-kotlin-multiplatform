package network

import data.Note
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
class NoteApiFake : NoteApi {
    override suspend fun getNotes(): List<Note> {
        delay(1500)
        return List(10) { it -> Note("Note $it") }
    }
}
