package com.akjaw.fullerstack.screens.common.base

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di

abstract class BaseFragment : KeyedFragment, DIAware {
    constructor() : super()
    constructor(@LayoutRes layoutRes: Int) : super(layoutRes)

    override val di: DI by di()

    fun hideKeyboard() {
        val focusedView = activity?.currentFocus ?: return
        val context = context ?: return
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}
