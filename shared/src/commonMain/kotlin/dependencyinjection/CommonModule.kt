package dependencyinjection

import feature.noteslist.FetchNotesListUseCaseAsync
import helpers.date.PatternProvider
import network.NoteApi
import network.NoteApiFake
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val common = DI.Module("Common") {
    bind<NoteApi>() with singleton { NoteApiFake() }
    bind() from singleton { FetchNotesListUseCaseAsync(instance()) }
    bind() from singleton { PatternProvider(instance()) }
}
