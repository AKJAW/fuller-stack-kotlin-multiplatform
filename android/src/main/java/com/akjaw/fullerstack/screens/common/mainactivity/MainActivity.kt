package com.akjaw.fullerstack.screens.common.mainactivity

import android.os.Bundle
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import com.akjaw.fullerstack.screens.common.base.BaseActivity
import com.akjaw.fullerstack.screens.noteslist.NotesListFragment
import org.kodein.di.instance

class MainActivity : BaseActivity() {

    private val viewMvcFactory: ViewMvcFactory by instance<ViewMvcFactory>()
    private lateinit var viewMvc: MainViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewMvc = viewMvcFactory.getMainViewMvc(null)
        setContentView(viewMvc.rootView)

        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_placeholder, NotesListFragment())
            fragmentTransaction.commit()
        }
    }
}
