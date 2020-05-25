package usecases

import data.Note
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FetchNotesListUseCaseAsync {

    sealed class FetchNotesListResult {
        class Success(val notes: List<Note>) : FetchNotesListResult()
        object Failure : FetchNotesListResult()
    }

    suspend fun executeAsync(backgroundDispatcher: CoroutineDispatcher): FetchNotesListResult {
        return withContext(backgroundDispatcher) {
            delay(500)
            val notes = List(10) { Note(it.toString()) }
            FetchNotesListResult.Success(notes)
        }
    }
}
