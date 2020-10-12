package composition

import database.DexieNoteDao
import database.NoteDao
import helpers.storage.LocalStorage
import helpers.storage.Storage
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import network.KtorClientNoteApi
import network.NoteApi
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

object KodeinEntry : DIAware {
    override val di by DI.lazy {
        import(common)
        bind<Storage>() with singleton { LocalStorage() }
        bind<HttpClient>() with singleton {
            HttpClient(Js) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
            }
        }
        bind<NoteApi>() with singleton { KtorClientNoteApi(instance()) }
        bind() from singleton { DexieNoteDao() }
        bind<NoteDao>() with singleton { instance<DexieNoteDao>() }
    }
}
