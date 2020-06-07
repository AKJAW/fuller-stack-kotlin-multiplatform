package com.akjaw.fullerstack.screens.common.base

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.di

abstract class BaseActivity : AppCompatActivity(), DIAware {
    override val di: DI by di()
}
