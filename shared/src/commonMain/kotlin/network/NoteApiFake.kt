package network

import com.soywiz.klock.DateTime
import data.Note
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
class NoteApiFake : NoteApi {
    private val notes = listOf(
        Note(title = "Note 1", creationDate = DateTime.createAdjusted(2020, 6, 2)),
        Note(title = "Note 2", creationDate = DateTime.createAdjusted(2020, 6, 2)),
        Note(title = "Note 3", creationDate = DateTime.createAdjusted(2020, 6, 5)),
        Note(title = "Note 4", creationDate = DateTime.createAdjusted(2020, 6, 7)),
        Note(title = "Note 5", creationDate = DateTime.createAdjusted(2020, 6, 8))
    )

    override suspend fun getNotes(): List<Note> {
        delay(1500)
        return notes
    }
}
