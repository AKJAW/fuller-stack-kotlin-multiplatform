package composition

import DexieNoteDao
import TokenProvider
import database.NoteDao
import feature.socket.NoteSocket
import helpers.date.NotesDatePatternStorageKey
import helpers.storage.LocalStorage
import helpers.storage.Storage
import io.ktor.client.HttpClient
import network.HttpClientFactory
import network.KtorClientNoteApi
import network.NoteApi
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import socket.KtorNoteSocket

object KodeinEntry : DIAware {
    override val di by DI.lazy {
        bind<Storage>() with singleton { LocalStorage() }
        bind<TokenProvider>() with singleton { TokenProvider() }
        bind<HttpClient>() with singleton { HttpClientFactory(instance()).create() }
        bind<NoteSocket>() with singleton { KtorNoteSocket(instance(), instance()) }
        bind<NoteApi>() with singleton { KtorClientNoteApi(instance()) }
        bind() from singleton { DexieNoteDao() }
        bind<NoteDao>() with singleton { instance<DexieNoteDao>() }
        bind<NotesDatePatternStorageKey>() with singleton {
            NotesDatePatternStorageKey("NotesDatePatternStorageKey")
        }
        import(common)
    }
}
