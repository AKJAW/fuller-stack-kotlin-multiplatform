package feature.list

import base.usecase.Either
import base.usecase.Failure
import kotlinx.coroutines.flow.Flow
import model.Note
import repository.NoteRepository

class NewFetchNotes(
    private val noteRepository: NoteRepository
) {
    suspend fun executeAsync(): Either<Failure, Flow<List<Note>>> {
        return try {
            Either.Right(noteRepository.getNotes())
        } catch (e: Exception) { // TODO make more defined
            Either.Left(Failure.ServerError)
        }
    }
}
