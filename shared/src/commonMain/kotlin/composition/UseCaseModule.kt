package composition

import feature.AddNote
import feature.DeleteNotes
import feature.GetNotes
import feature.UpdateNote
import feature.local.search.SearchNotes
import feature.local.sort.SortNotes
import feature.socket.ListenToSocketUpdates
import feature.synchronization.SynchronizeAddedNotes
import feature.synchronization.SynchronizeApiAndLocalNotes
import feature.synchronization.SynchronizeDeletedNotes
import feature.synchronization.SynchronizeNotes
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
    bind() from singleton {
        SynchronizeDeletedNotes(instance("BackgroundDispatcher"), instance(), instance(), instance())
    }
    bind() from singleton { SynchronizeAddedNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind() from singleton { SynchronizeUpdatedNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind<SynchronizeNotes>() with singleton {
        SynchronizeApiAndLocalNotes(
            coroutineDispatcher = instance("BackgroundDispatcher"),
            noteDao = instance(),
            noteApi = instance(),
            synchronizeDeletedNotes = instance(),
            synchronizeAddedNotes = instance(),
            synchronizeUpdatedNotes = instance()
        )
    }
    bind() from singleton {
        ListenToSocketUpdates(instance("BackgroundDispatcher"), instance(), instance(), instance())
    }
    bind() from singleton { SearchNotes() }
    bind() from singleton { SortNotes() }
}
