package com.akjaw.fullerstack.screens.common.base

import androidx.appcompat.app.AppCompatActivity
import com.akjaw.framework.composition.lifecycleModule
import com.akjaw.fullerstack.authentication.composition.activityScopedAuthenticationModule
import com.akjaw.fullerstack.screens.common.FullerStackApp
import org.kodein.di.DI
import org.kodein.di.DIAware

abstract class BaseActivity : AppCompatActivity(), DIAware {

    override val di: DI by DI.lazy {
        val customApplication = application as FullerStackApp
        extend(customApplication.di)
        import(lifecycleModule())
        import(activityScopedAuthenticationModule)
    }
}
