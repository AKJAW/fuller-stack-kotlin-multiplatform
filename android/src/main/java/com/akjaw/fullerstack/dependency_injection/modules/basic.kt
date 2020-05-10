package com.akjaw.fullerstack.dependency_injection.modules

import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

val basic = Kodein.Module("basic") {
    bind() from singleton { ViewMvcFactory(instance()) }
}