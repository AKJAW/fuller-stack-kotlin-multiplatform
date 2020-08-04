package composition

import base.CommonDispatchers
import database.NoteEntityMapper
import helpers.date.KlockTimestampProvider
import helpers.date.PatternProvider
import helpers.date.TimestampProvider
import helpers.validation.NoteEditorInputValidator
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import model.NoteIdentifierMapper
import network.NoteSchemaMapper
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

@ExperimentalCoroutinesApi
val common = DI.Module("Common") {
    bind() from singleton { NoteIdentifierMapper() }
    bind() from singleton { NoteSchemaMapper() }
    bind() from singleton { NoteEntityMapper() }
    bind<CoroutineDispatcher>(tag = "BackgroundDispatcher") with singleton { CommonDispatchers.BackgroundDispatcher }
    bind<TimestampProvider>() with singleton { KlockTimestampProvider() }
    bind() from singleton { PatternProvider(instance()) }
    bind<NoteInputValidator>() with singleton { NoteEditorInputValidator() }
    import(useCaseModule)
}
