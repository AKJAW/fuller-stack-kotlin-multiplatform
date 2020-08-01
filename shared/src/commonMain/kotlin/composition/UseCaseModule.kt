package composition

import feature.NewAddNote
import feature.NewDeleteNotes
import feature.NewUpdateNote
import feature.editor.AddNote
import feature.editor.UpdateNote
import feature.list.DeleteNotes
import feature.list.FetchNotes
import feature.list.RefreshNotes
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val useCaseModule = DI.Module("UseCaseModule") {
    bind() from singleton { FetchNotes(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton { RefreshNotes(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton { DeleteNotes(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton {
        NewDeleteNotes(instance("BackgroundDispatcher"), instance(), instance())
    }
    bind() from singleton { AddNote(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton {
        NewAddNote(instance("BackgroundDispatcher"), instance(), instance(), instance(), instance())
    }
    bind() from singleton { UpdateNote(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton {
        NewUpdateNote(instance("BackgroundDispatcher"), instance(), instance(), instance())
    }
}
