package feature.socket

import database.NoteDao
import feature.synchronization.SynchronizeNotes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch

class ListenToSocketUpdates(
    coroutineDispatcher: CoroutineDispatcher,
    private val noteDao: NoteDao,
    private val noteSocket: NoteSocket,
    private val synchronizeNotes: SynchronizeNotes
) {
    private val scope: CoroutineScope = CoroutineScope(coroutineDispatcher)
    private var job: Job? = null

    fun listenToSocketChanges() {
        if (job != null) {
            return
        }
        job = scope.launch {
            noteSocket.getNotesFlow()
                .retry {
                    delay(5000)
                    true
                }
                .collect { apiNotes ->
                    val localNotes = noteDao.getAllNotes().firstOrNull()
                    if (localNotes != null) {
                        synchronizeNotes.executeAsync(localNotes, apiNotes)
                    }
                }
        }
    }

    fun close() {
        noteSocket.close()
        scope.cancel()
    }
}
