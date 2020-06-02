package dependencyinjection

import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton
import usecases.FetchNotesListUseCaseAsync

val common = Kodein.Module("Common") {
    bind() from singleton { FetchNotesListUseCaseAsync() }
}
