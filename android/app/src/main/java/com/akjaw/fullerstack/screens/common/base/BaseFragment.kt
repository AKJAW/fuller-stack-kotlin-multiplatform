package com.akjaw.fullerstack.screens.common.base

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import com.akjaw.framework.utility.KeyboardCloser
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

abstract class BaseFragment : KeyedFragment, DIAware {
    constructor() : super()
    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    override val di: DI by di()
    protected val keyboardCloser: KeyboardCloser by instance()

    fun hideKeyboard() {
        keyboardCloser.close()
    }
}
