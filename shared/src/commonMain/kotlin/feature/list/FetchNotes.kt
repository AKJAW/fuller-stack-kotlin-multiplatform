package feature.list

import base.usecase.Failure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import model.Note
import repository.NoteRepository

class FetchNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteRepository: NoteRepository
) {
    sealed class Result {
        object Loading: Result()
        data class Error(val failure: Failure) : Result()
        data class Content(val notesFlow: Flow<List<Note>>) : Result()
    }

    @Suppress("TooGenericExceptionCaught")
    suspend fun executeAsync(): Flow<Result> = withContext(coroutineDispatcher){
        flow {
            emit(Result.Loading)

            val result = try {
                Result.Content(noteRepository.getNotes())
            } catch (e: Exception) { // TODO make more defined
                Result.Error(Failure.ServerError)
            }
            emit(result)
        }
    }
}
