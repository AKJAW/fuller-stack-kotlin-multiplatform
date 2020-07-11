package feature.list

import base.CommonDispatchers
import base.usecase.Either
import base.usecase.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import model.Note
import repository.NoteRepository

class FetchNotes(
    private val noteRepository: NoteRepository
) {
    sealed class Result {
        object Loading: Result()
        data class Error(val failure: Failure) : Result()
        data class Content(val notesFlow: Flow<List<Note>>) : Result()
    }

    suspend fun executeTest(): Flow<Result> = withContext(CommonDispatchers.BackgroundDispatcher){
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

    suspend fun executeAsync(): Either<Failure, Flow<List<Note>>> {
        return try {
            Either.Right(noteRepository.getNotes())
        } catch (e: Exception) { // TODO make more defined
            Either.Left(Failure.ServerError)
        }
    }
}
