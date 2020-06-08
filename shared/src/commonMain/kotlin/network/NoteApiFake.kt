package network

import com.soywiz.klock.DateTime
import data.Note
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
class NoteApiFake : NoteApi {
    private val notes = listOf(
        Note("Note 1", DateTime.createAdjusted(2020, 6, 2)),
        Note("Note 2", DateTime.createAdjusted(2020, 6, 2)),
        Note("Note 3", DateTime.createAdjusted(2020, 6, 5)),
        Note("Note 4", DateTime.createAdjusted(2020, 6, 7)),
        Note("Note 5", DateTime.createAdjusted(2020, 6, 8))
    )

    override suspend fun getNotes(): List<Note> {
        delay(1500)
        return notes
    }
}
