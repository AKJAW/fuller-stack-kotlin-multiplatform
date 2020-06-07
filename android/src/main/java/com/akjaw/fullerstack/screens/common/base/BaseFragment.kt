package com.akjaw.fullerstack.screens.common.base

import androidx.fragment.app.Fragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

abstract class BaseFragment : Fragment(), DIAware {
    override val di: DI by di()
}
