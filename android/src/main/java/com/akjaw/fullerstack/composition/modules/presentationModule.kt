package com.akjaw.fullerstack.composition.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akjaw.fullerstack.screens.common.ViewModelFactory
import com.akjaw.fullerstack.screens.editor.NoteEditorViewModel
import com.akjaw.fullerstack.screens.list.NotesListViewModel
import com.akjaw.fullerstack.screens.list.recyclerview.NotesListAdapterFactory
import com.akjaw.fullerstack.screens.list.recyclerview.NotesSelectionTrackerFactory
import com.akjaw.fullerstack.screens.list.recyclerview.selection.NotesListActionMode
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val presentationModule = DI.Module("presentationModule") {
    bind() from singleton { NotesListActionMode(instance("FragmentActivity")) }
    bind() from singleton { NotesSelectionTrackerFactory(instance("FragmentActivity"), instance(), instance()) }
    bind() from singleton { NotesListAdapterFactory(instance(), instance(), instance()) }
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(di.direct) }
    bind<ViewModel>(tag = NotesListViewModel::class.java.simpleName) with provider {
        NotesListViewModel(instance("ApplicationCoroutineScope"), instance(), instance(), instance())
    }
    bind<ViewModel>(tag = NoteEditorViewModel::class.java.simpleName) with provider {
        NoteEditorViewModel(instance("ApplicationCoroutineScope"), instance(), instance(), instance())
    }
}
