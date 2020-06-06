package dependencyinjection

import feature.noteslist.FetchNotesListUseCaseAsync
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

val common = Kodein.Module("Common") {
    bind() from singleton { FetchNotesListUseCaseAsync() }
}
