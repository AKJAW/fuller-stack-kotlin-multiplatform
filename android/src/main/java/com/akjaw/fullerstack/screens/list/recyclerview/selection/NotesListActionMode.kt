package com.akjaw.fullerstack.screens.list.recyclerview.selection

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import com.akjaw.fullerstack.android.R

class NotesListActionMode(
    private val fragmentActivity: FragmentActivity
) : ActionMode.Callback {

    private var actionMode: ActionMode? = null
    private lateinit var onActionModeDestroyed: () -> Unit
    private lateinit var onDeleteClicked: () -> Unit

    fun initialize(
        onDeleteClicked: () -> Unit,
        onActionModeDestroyed: () -> Unit
    ) {
        this.onDeleteClicked = onDeleteClicked
        this.onActionModeDestroyed = onActionModeDestroyed
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        actionMode = mode
        val inflater = mode?.menuInflater
        inflater?.inflate(R.menu.note_list_selection, menu)
        // TODO title
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        onActionModeDestroyed.invoke()
        actionMode = null
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note) {
            onDeleteClicked()
        }
        return true
    }

    fun startActionMode() {
        if (actionMode == null) {
            fragmentActivity.startActionMode(this)
        }
    }

    fun exitActionMode() {
        actionMode?.finish()
    }

    fun setTitle(title: String) {
        actionMode?.title = title
    }
}
