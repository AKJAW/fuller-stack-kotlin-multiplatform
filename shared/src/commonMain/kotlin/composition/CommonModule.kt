package composition

import base.CommonDispatchers
import database.NoteEntityMapper
import helpers.date.KlockUnixTimestampProvider
import helpers.date.PatternProvider
import helpers.date.UnixTimestampProvider
import helpers.validation.NoteEditorInputValidator
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import network.NoteSchemaMapper
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@ExperimentalCoroutinesApi
val common = DI.Module("Common") {
    bind() from singleton { NoteSchemaMapper() }
    bind() from singleton { NoteEntityMapper() }
    bind<CoroutineDispatcher>(tag = "BackgroundDispatcher") with singleton { CommonDispatchers.BackgroundDispatcher }
    bind<UnixTimestampProvider>() with singleton { KlockUnixTimestampProvider() }
    bind() from singleton { PatternProvider(instance()) }
    bind<NoteInputValidator>() with singleton { NoteEditorInputValidator() }
    import(useCaseModule)
}
