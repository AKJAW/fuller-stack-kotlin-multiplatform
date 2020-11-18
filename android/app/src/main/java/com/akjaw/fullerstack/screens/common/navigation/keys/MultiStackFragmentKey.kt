package com.akjaw.fullerstack.screens.common.navigation.keys

import android.os.Bundle
import android.os.Parcelable
import com.akjaw.fullerstack.screens.common.base.BaseFragment

abstract class MultiStackFragmentKey : Parcelable {

    enum class RootFragments {
        HOME,
        PROFILE,
        SETTINGS
    }

    val fragmentTag
        get() = "${getKeyIdentifier()}_${toString()}"

    fun newFragment(): BaseFragment = instantiateFragment().apply {
        arguments = (arguments ?: Bundle()).also { bundle ->
            bundle.putParcelable("FRAGMENT_KEY", this@MultiStackFragmentKey)
        }
    }

    abstract fun getKeyIdentifier(): String

    abstract fun instantiateFragment(): BaseFragment
}
