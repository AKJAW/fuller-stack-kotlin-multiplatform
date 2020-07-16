package feature.list

import base.usecase.Failure
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import model.Note
import network.NetworkResponse
import network.safeApiCall
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

            val networkResponse = safeApiCall { noteRepository.getNotes() }

            val result = when(networkResponse) {
                is NetworkResponse.Success -> Result.Content(networkResponse.result)
                is NetworkResponse.ApiError -> Result.Error(Failure.ApiError)
                else -> Result.Error(Failure.NetworkError)
            }

            emit(result)
        }
    }
}
