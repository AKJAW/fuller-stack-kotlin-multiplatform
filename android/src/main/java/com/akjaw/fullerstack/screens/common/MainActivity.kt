package com.akjaw.fullerstack.screens.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.notes_list.NotesListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO move to mvc
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.fragment_placeholder, NotesListFragment())
            fragmentTransaction.commit()
        }

    }
}
