package usecases

import base.Either
import base.Failure
import base.UseCaseAsync
import data.Note
import kotlinx.coroutines.delay

class FetchNotesListUseCaseAsync : UseCaseAsync<UseCaseAsync.None, List<Note>>() {
    override suspend fun run(params: None): Either<Failure, List<Note>> {
        delay(500)
        val notes = List(10) { Note(it.toString()) }
        return Either.Right(notes)
    }
}
