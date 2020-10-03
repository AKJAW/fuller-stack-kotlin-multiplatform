package com.akjaw.fullerstack.screens.common.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.akjaw.fullerstack.composition.modules.navigatorModule
import com.akjaw.fullerstack.composition.modules.presentationModule
import com.akjaw.fullerstack.screens.common.FullerStackApp
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.singleton

abstract class BaseActivity : AppCompatActivity(), DIAware {
    override val di: DI by DI.lazy {
        val customApplication = application as FullerStackApp
        extend(customApplication.di)
        bind<FragmentActivity>("FragmentActivity") with singleton { this@BaseActivity }
        bind<Context>("Context") with singleton { this@BaseActivity }
        import(navigatorModule)
        import(presentationModule)
    }
}
