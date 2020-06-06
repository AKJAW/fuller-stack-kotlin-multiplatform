package feature.noteslist

import base.usecase.Either
import base.usecase.Failure
import base.usecase.UseCaseAsync
import data.Note
import network.NoteApi

class FetchNotesListUseCaseAsync(
    private val noteApi: NoteApi
) : UseCaseAsync<UseCaseAsync.None, List<Note>>() {
    override suspend fun run(params: None): Either<Failure, List<Note>> {
        return try {
            val notes = noteApi.getNotes()
            Either.Right(notes)
        } catch (e: Throwable) { //TODO make more defined
            Either.Left(Failure.ServerError)
        }
    }
}
