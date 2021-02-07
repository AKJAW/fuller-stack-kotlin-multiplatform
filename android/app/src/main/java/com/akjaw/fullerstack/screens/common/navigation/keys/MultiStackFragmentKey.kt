package com.akjaw.fullerstack.screens.common.navigation.keys

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

abstract class MultiStackFragmentKey : Parcelable {

    enum class RootFragments {
        HOME,
        PROFILE,
        SETTINGS
    }

    val fragmentTag
        get() = "${getKeyIdentifier()}_${toString()}"

    fun newFragment(): Fragment = instantiateFragment().apply {
        arguments = (arguments ?: Bundle()).also { bundle ->
            bundle.putParcelable("FRAGMENT_KEY", this@MultiStackFragmentKey)
        }
    }

    abstract fun getKeyIdentifier(): String

    abstract fun instantiateFragment(): Fragment
}
