package com.akjaw.fullerstack.dependencyinjection.modules

import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val basic = DI.Module("basic") {
    bind() from singleton { ViewMvcFactory(instance()) }
}
