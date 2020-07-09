package com.akjaw.fullerstack.screens.common.base

import androidx.annotation.LayoutRes
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

abstract class BaseFragment : KeyedFragment, DIAware {
    constructor() : super()
    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    override val di: DI by di()
}
