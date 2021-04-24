package server.composition

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import server.storage.DatabaseFactory
import server.storage.ExposedDatabase
import server.storage.NotesStorage

val databaseModule = DI.Module("databaseModule") {
    bind<ExposedDatabase>() with singleton { DatabaseFactory().create() }
    bind() from singleton { NotesStorage(instance(), instance()) }
}
