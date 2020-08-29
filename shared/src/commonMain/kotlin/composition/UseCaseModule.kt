package composition

import feature.AddNote
import feature.DeleteNotes
import feature.GetNotes
import feature.SynchronizeNotes
import feature.UpdateNote
import feature.synchronization.SynchronizeAddedNotes
import feature.synchronization.SynchronizeDeletedNotes
import feature.synchronization.SynchronizeUpdatedNotes
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val useCaseModule = DI.Module("UseCaseModule") {
    bind() from singleton { GetNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind() from singleton { DeleteNotes(instance("BackgroundDispatcher"), instance(), instance(), instance()) }
    bind() from singleton { AddNote(instance("BackgroundDispatcher"), instance(), instance(), instance()) }
    bind() from singleton { UpdateNote(instance("BackgroundDispatcher"), instance(), instance(), instance()) }
    bind() from singleton { SynchronizeDeletedNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind() from singleton { SynchronizeAddedNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind() from singleton { SynchronizeUpdatedNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind() from singleton { SynchronizeNotes(
        coroutineDispatcher = instance("BackgroundDispatcher"),
        noteDao = instance(),
        noteApi = instance(),
        synchronizeDeletedNotes = instance(),
        synchronizeAddedNotes = instance(),
        synchronizeUpdatedNotes = instance()
    ) }
}
