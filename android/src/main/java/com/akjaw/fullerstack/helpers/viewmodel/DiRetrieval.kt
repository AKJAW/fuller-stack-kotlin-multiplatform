package com.akjaw.fullerstack.helpers.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.instance

inline fun <reified VM : ViewModel, T> T.viewModels(): Lazy<VM> where T : DIAware, T : Fragment {
    return lazy { ViewModelProvider(this, direct.instance()).get(VM::class.java) }
}
