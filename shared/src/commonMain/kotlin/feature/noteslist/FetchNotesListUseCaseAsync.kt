package feature.noteslist

import base.Either
import base.Failure
import base.UseCaseAsync
import data.Note
import kotlinx.coroutines.delay

@Suppress("MagicNumber")
class FetchNotesListUseCaseAsync : UseCaseAsync<UseCaseAsync.None, List<Note>>() {
    override suspend fun run(params: None): Either<Failure, List<Note>> {
        delay(1500)
        val notes = List(10) { Note(it.toString()) }
        return Either.Right(notes)
    }
}
