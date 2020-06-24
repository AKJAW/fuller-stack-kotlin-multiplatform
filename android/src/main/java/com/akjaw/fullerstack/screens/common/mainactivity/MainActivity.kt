package com.akjaw.fullerstack.screens.common.mainactivity

import android.os.Bundle
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseActivity
import com.akjaw.fullerstack.screens.common.navigation.keys.NotesListScreen
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import org.kodein.di.instance

class MainActivity : BaseActivity(), SimpleStateChanger.NavigationHandler {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private lateinit var viewMvc: MainViewMvc
    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc = viewMvcFactory.getMainViewMvc(null)
        setContentView(viewMvc.rootView)

        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, R.id.fragment_placeholder)
        Navigator.configure()
            .setStateChanger(SimpleStateChanger(this))
            .install(this, viewMvc.rootView.findViewById(R.id.root), History.of(NotesListScreen()))
    }

    override fun onBackPressed() {
        if(!Navigator.onBackPressed(this)){
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }

}
