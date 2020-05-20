package com.akjaw.fullerstack.screens.common.base

import androidx.fragment.app.Fragment
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein

abstract class BaseFragment : Fragment(), KodeinAware {
    override val kodein: Kodein by kodein()
}
