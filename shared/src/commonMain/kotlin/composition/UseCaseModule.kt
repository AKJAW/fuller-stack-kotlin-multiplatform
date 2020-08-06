package composition

import feature.AddNote
import feature.DeleteNotes
import feature.GetNotes
import feature.SynchronizeNotes
import feature.UpdateNote
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val useCaseModule = DI.Module("UseCaseModule") {
    bind() from singleton { GetNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind() from singleton { DeleteNotes(instance("BackgroundDispatcher"), instance(), instance()) }
    bind() from singleton { AddNote(instance("BackgroundDispatcher"), instance(), instance(), instance()) }
    bind() from singleton { UpdateNote(instance("BackgroundDispatcher"), instance(), instance(), instance()) }
    bind() from singleton { SynchronizeNotes(instance("BackgroundDispatcher"), instance(), instance()) }
}
