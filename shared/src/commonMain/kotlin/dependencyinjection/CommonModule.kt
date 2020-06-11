package dependencyinjection

import feature.noteslist.AddNote
import feature.noteslist.FetchNotes
import feature.noteslist.RefreshNotes
import helpers.date.PatternProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import network.NoteApi
import network.NoteApiFake
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import repository.NoteRepository
import repository.NoteRepositoryFake

@ExperimentalCoroutinesApi
val common = DI.Module("Common") {
    bind<NoteApi>() with singleton { NoteApiFake() }
    bind<NoteRepository>() with singleton { NoteRepositoryFake(instance()) }
    bind() from singleton { RefreshNotes(instance()) }
    bind() from singleton { FetchNotes(instance()) }
    bind() from singleton { AddNote(instance()) }
    bind() from singleton { PatternProvider(instance()) }
}
