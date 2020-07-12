package composition

import base.CommonDispatchers
import feature.editor.AddNote
import feature.editor.UpdateNote
import feature.list.FetchNotes
import feature.list.RefreshNotes
import helpers.date.PatternProvider
import helpers.validation.NoteEditorInputValidator
import helpers.validation.NoteInputValidator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import repository.NoteRepository
import repository.NoteRepositoryImpl

@ExperimentalCoroutinesApi
val common = DI.Module("Common") {
    bind<NoteRepository>() with singleton { NoteRepositoryImpl(instance()) }
    bind<CoroutineDispatcher>(tag = "BackgroundDispatcher") with singleton { CommonDispatchers.BackgroundDispatcher }
    bind() from singleton { FetchNotes(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton { RefreshNotes(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton { AddNote(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton { UpdateNote(instance("BackgroundDispatcher"), instance()) }
    bind() from singleton { PatternProvider(instance()) }
    bind<NoteInputValidator>() with singleton { NoteEditorInputValidator() }
}
