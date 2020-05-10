package com.akjaw.fullerstack.screens.common.base

import android.content.Context
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes

abstract class BaseViewMvc {

    abstract val rootView: View

    protected val context: Context
        get() = rootView.context

    protected fun <T: View> findViewById(@IdRes id: Int): T = rootView.findViewById(id)

    protected fun getString(@StringRes id: Int): String = context.getString(id)

}
