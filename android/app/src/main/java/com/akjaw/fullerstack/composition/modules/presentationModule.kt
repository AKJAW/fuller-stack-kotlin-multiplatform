package com.akjaw.fullerstack.composition.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akjaw.framework.utility.KeyboardCloser
import com.akjaw.fullerstack.screens.common.ViewModelFactory
import com.akjaw.fullerstack.screens.common.main.MainActivityViewModel
import com.akjaw.fullerstack.screens.editor.NoteEditorViewModel
import com.akjaw.fullerstack.screens.list.NotesListViewModel
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapterFactory
import com.akjaw.fullerstack.screens.list.recyclerview.NotesSelectionTrackerFactory
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import com.akjaw.fullerstack.screens.profile.ProfileViewModel
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val presentationModule = DI.Module("presentationModule") {
    bind() from singleton { NotesListActionMode(instance(), instance()) }
    bind() from singleton { NotesSelectionTrackerFactory(instance(), instance()) }
    bind() from singleton { NotesListAdapterFactory(instance()) }
    bind() from singleton { KeyboardCloser(instance()) }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(di.direct) }
    bind<ViewModel>(tag = NotesListViewModel::class.java.simpleName) with provider {
        NotesListViewModel(
            applicationScope = instance("ApplicationCoroutineScope"),
            getNotes = instance(),
            deleteNotes = instance(),
            synchronizeNotes = instance(),
            searchNotes = instance(),
            sortNotes = instance()
        )
    }
    bind<ViewModel>(tag = NoteEditorViewModel::class.java.simpleName) with provider {
        NoteEditorViewModel(instance("ApplicationCoroutineScope"), instance(), instance(), instance())
    }
    bind<ViewModel>(tag = ProfileViewModel::class.java.simpleName) with provider {
        ProfileViewModel(instance(), instance(), instance())
    }
    bind<ViewModel>(tag = MainActivityViewModel::class.java.simpleName) with provider {
        MainActivityViewModel(instance())
    }
}
