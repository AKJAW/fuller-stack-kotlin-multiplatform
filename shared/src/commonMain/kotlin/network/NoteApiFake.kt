package network

import com.soywiz.klock.DateTime
import kotlinx.coroutines.delay
import model.Note

@Suppress("MagicNumber")
class NoteApiFake : NoteApi {
    private val notes = listOf(
        Note(id = 0, title = "Note 1", creationDate = DateTime.createAdjusted(2020, 6, 2)),
        Note(id = 1, title = "Note 2", creationDate = DateTime.createAdjusted(2020, 6, 2)),
        Note(id = 2, title = "Note 3", creationDate = DateTime.createAdjusted(2020, 6, 5)),
        Note(id = 3, title = "Note 4", creationDate = DateTime.createAdjusted(2020, 6, 7)),
        Note(id = 4, title = "Note 5", creationDate = DateTime.createAdjusted(2020, 6, 8))
    )

    override suspend fun getNotes(): List<Note> {
        delay(1500)
        return notes
    }
}
