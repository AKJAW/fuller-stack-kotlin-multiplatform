package feature

import base.usecase.Failure
import database.NoteDao
import database.NoteEntityMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import model.Note

class NewGetNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteEntityMapper: NoteEntityMapper
) {
    @Deprecated("Will be deleted")
    sealed class Result {
        object Loading : Result()
        data class Error(val failure: Failure) : Result()
        data class Content(val notesFlow: Flow<List<Note>>) : Result()
    }

    suspend fun executeAsync(): Flow<List<Note>> = withContext(coroutineDispatcher) {
        return@withContext noteDao.getAllNotes()
            .map { entities ->
                val availableEntities = entities.filterNot { it.wasDeleted }
                noteEntityMapper.toNotes(availableEntities)
            }
    }
}
