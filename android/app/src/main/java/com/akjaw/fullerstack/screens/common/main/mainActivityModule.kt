package com.akjaw.fullerstack.screens.common.main

import com.akjaw.fullerstack.composition.modules.navigatorModule
import com.akjaw.fullerstack.composition.modules.presentationModule
import org.kodein.di.DI

val mainActivityModule = DI.Module("mainActivityModule") {
    import(navigatorModule)
    import(presentationModule)
}
