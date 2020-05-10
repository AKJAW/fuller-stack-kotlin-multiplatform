package com.akjaw.fullerstack.screens.common

import android.app.Application
import com.akjaw.fullerstack.dependency_injection.modules.basic
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class CustomApplication: Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidXModule(this@CustomApplication))
        import(basic)
    }
}