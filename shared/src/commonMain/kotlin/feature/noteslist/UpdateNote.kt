package feature.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import model.Note
import repository.NoteRepository

class UpdateNote(
    private val noteRepository: NoteRepository
) : UseCaseAsync<Note, UseCaseAsync.None>() {

    override suspend fun run(params: Note): Either<Failure, None> {
        noteRepository.updateNote(params)
        return Either.Right(None())
    }
}
