package feature.editor

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import model.Note
import repository.NoteRepository

class AddNote(
    private val noteRepository: NoteRepository
) : UseCaseAsync<Note, UseCaseAsync.None>() {

    override suspend fun run(params: Note): Either<Failure, None> {
        noteRepository.addNote(params)
        return Either.Right(None())
    }
}
