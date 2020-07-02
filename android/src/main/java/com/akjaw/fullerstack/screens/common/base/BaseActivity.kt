package com.akjaw.fullerstack.screens.common.base

import androidx.appcompat.app.AppCompatActivity
import com.akjaw.fullerstack.dependencyinjection.modules.navigatorModule
import com.akjaw.fullerstack.dependencyinjection.modules.presentationModule
import com.akjaw.fullerstack.screens.common.CustomApplication
import org.kodein.di.DI
import org.kodein.di.DIAware

abstract class BaseActivity : AppCompatActivity(), DIAware {
    override val di: DI by DI.lazy {
        val customApplication = application as CustomApplication
        extend(customApplication.di)
        import(navigatorModule)
        import(presentationModule)
    }
}
