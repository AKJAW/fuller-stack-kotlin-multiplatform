package com.akjaw.fullerstack.screens.common.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.StringRes

abstract class BaseViewMvc {

    abstract val rootView: View

    protected val context: Context
        get() = rootView.context

    protected fun <T : View> findViewById(@IdRes id: Int): T = rootView.findViewById(id)

    protected fun getString(@StringRes id: Int): String = context.getString(id)

    fun hideKeyboard() {
        val context = rootView.context
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        val windowToken = rootView.windowToken
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
