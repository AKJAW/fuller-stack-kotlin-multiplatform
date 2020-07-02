package feature.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import repository.NoteRepository

class RefreshNotes(
    private val noteRepository: NoteRepository
) : UseCaseAsync<UseCaseAsync.None, UseCaseAsync.None>() {
    override suspend fun run(params: None): Either<Failure, None> {
        return try {
            noteRepository.refreshNotes()
            Either.Right(None())
        } catch (e: Throwable) { // TODO make more defined
            Either.Left(Failure.ServerError)
        }
    }
}
