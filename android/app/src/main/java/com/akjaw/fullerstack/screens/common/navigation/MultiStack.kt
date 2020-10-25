package com.akjaw.fullerstack.screens.common.navigation

import com.akjaw.fullerstack.screens.common.navigation.keys.MultiStackFragmentKey
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.Bundleable
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.StateChanger
import com.zhuinden.statebundle.StateBundle

/* Credit https://github.com/Zhuinden/simple-stack */
class MultiStack(rootKeys: List<MultiStackFragmentKey>) : Bundleable {
    //TODO replace with a list builder from Kotlin 1.4
    private val backStackMap: Map<String, Backstack>

    private var selectedStack: String? = rootKeys.first().getKeyIdentifier()
    private var stateChanger: StateChanger? = null

    private var isPaused = false

    init {
        backStackMap = rootKeys.map { key ->
            val backstack = Backstack().apply {
                val history = History.single(key)
                setup(history)
            }
            val identifier = key.getKeyIdentifier()
            identifier to backstack
        }.toMap()
    }

    fun getSelectedStack(): Backstack = backStackMap.get(selectedStack)!!

    fun setStateChanger(stateChanger: StateChanger?) {
        this.stateChanger = stateChanger
        for ((key, value) in backStackMap) {
            if (key != selectedStack) {
                value.detachStateChanger()
            } else {
                value.setStateChanger(stateChanger)
            }
        }
    }

    fun resume() {
        isPaused = false
        for ((key, value) in backStackMap) {
            if (key != selectedStack) {
                value.detachStateChanger()
            } else {
                value.reattachStateChanger()
            }
        }
    }

    fun pause() {
        isPaused = true
        for ((_, value) in backStackMap) {
            value.detachStateChanger()
        }
    }

    fun executePendingStateChange() {
        for ((_, backstack) in backStackMap) {
            backstack.executePendingStateChange()
        }
    }

    fun setSelectedStack(identifier: String) {
        if (!backStackMap.containsKey(identifier)) {
            throw IllegalArgumentException("You cannot specify a stack [$identifier] that does not exist!")
        }
        if (selectedStack != identifier) {
            this.selectedStack = identifier
            setStateChanger(stateChanger)
        }
    }

    override fun toBundle(): StateBundle = StateBundle().apply {
        putString("multistack_selectedStack", selectedStack)
        for ((identifier, backstack) in backStackMap.entries) {
            putBundle("multistack_identifier_$identifier", backstack.toBundle())
        }
    }

    override fun fromBundle(bundle: StateBundle?) {
        bundle?.run {
            selectedStack = getString("multistack_selectedStack")
            for ((identifier, backstack) in backStackMap.entries) {
                backstack.fromBundle(getBundle("multistack_identifier_$identifier"))
            }
        }
    }
}
