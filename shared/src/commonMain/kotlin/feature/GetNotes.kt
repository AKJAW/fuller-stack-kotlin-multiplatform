package feature

import database.NoteDao
import database.NoteEntityMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import model.Note

class GetNotes(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteEntityMapper: NoteEntityMapper
) {

    suspend fun executeAsync(): Flow<List<Note>> = withContext(coroutineDispatcher) {
        return@withContext noteDao.getAllNotes()
            .map { entities ->
                val availableEntities = entities.filterNot { it.wasDeleted }
                noteEntityMapper.toNotes(availableEntities)
            }
    }
}
