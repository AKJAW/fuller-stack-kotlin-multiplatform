package dependencyinjection

import feature.noteslist.FetchNotesListUseCaseAsync
import network.NoteApi
import network.NoteApiFake
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

val common = Kodein.Module("Common") {
    bind<NoteApi>() with singleton { NoteApiFake() }
    bind() from singleton { FetchNotesListUseCaseAsync(instance()) }
}
