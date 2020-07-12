package composition

import helpers.storage.LocalStorage
import helpers.storage.Storage
import network.NoteApi
import network.NoteApiFake
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton

object KodeinEntry : DIAware {
    override val di by DI.lazy {
        import(common)
        bind<Storage>() with singleton { LocalStorage() }
        bind<NoteApi>() with singleton { NoteApiFake() }
    }
}
