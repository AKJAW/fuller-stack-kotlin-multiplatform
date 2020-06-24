package com.akjaw.fullerstack.dependencyinjection.modules

import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.list.NotesListController
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val presentationModule = DI.Module("presentationModule") {
    bind() from singleton { ViewMvcFactory(instance(), instance()) }
    bind() from singleton { NotesListController(instance(), instance()) }
}
