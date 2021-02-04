package feature.synchronization

import database.NoteEntity
import feature.synchronization.SynchronizeNotes.Result
import network.NoteSchema

class SynchronizeNotesMock : SynchronizeNotes {

    var shouldFail = false

    var executeAsyncNoParamsCalled = false
        private set
    var executeAsyncWithParamsCalled = false
        private set

    var passedInLocalNotes: List<NoteEntity>? = null
        private set
    var passedInApiNotes: List<NoteSchema>? = null
        private set

    override suspend fun executeAsync(): Result {
        executeAsyncNoParamsCalled = true
        return getResult()
    }

    override suspend fun executeAsync(
        localNotes: List<NoteEntity>,
        apiNotes: List<NoteSchema>
    ): Result {
        executeAsyncWithParamsCalled = true
        passedInLocalNotes = localNotes
        passedInApiNotes = apiNotes
        return getResult()
    }

    private fun getResult(): Result = if (shouldFail) Result.SynchronizationFailed else Result.Success
}
