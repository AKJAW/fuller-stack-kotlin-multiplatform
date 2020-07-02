package feature.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import data.Note
import kotlinx.coroutines.flow.Flow
import repository.NoteRepository

class FetchNotes(
    private val noteRepository: NoteRepository
) : UseCaseAsync<UseCaseAsync.None, Flow<List<Note>>>() {
    override suspend fun run(params: None): Either<Failure, Flow<List<Note>>> {
        return try {
            Either.Right(noteRepository.getNotes())
        } catch (e: Exception) { // TODO make more defined
            Either.Left(Failure.ServerError)
        }
    }
}
