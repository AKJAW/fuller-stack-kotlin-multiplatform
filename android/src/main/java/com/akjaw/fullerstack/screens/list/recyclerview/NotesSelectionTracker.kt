package com.akjaw.fullerstack.screens.list.recyclerview

import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.akjaw.fullerstack.android.R

class NotesSelectionTracker(
    private val onDestroyActionMode: () -> Unit
) : ActionMode.Callback {

    private var actionMode: ActionMode? = null
    private var selectedNoteIds: MutableList<Int> = mutableListOf() //TODO preserve on save instance state

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        actionMode = mode
        val inflater = mode?.menuInflater
        inflater?.inflate(R.menu.note_list_selection, menu)
        //TODO title
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        selectedNoteIds.clear()
        actionMode = null
        onDestroyActionMode()
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note) {
            Log.d("Delete note", selectedNoteIds.toString())
        }
        return true
    }

    fun isSelectionModeEnabled(): Boolean = selectedNoteIds.isNotEmpty()

    fun isSelected(noteId: Int): Boolean = selectedNoteIds.contains(noteId)

    fun selectNote(noteId: Int, view: View) {
        if(selectedNoteIds.contains(noteId)){
            selectedNoteIds.remove(noteId)
        } else {
            selectedNoteIds.add(noteId)
        }
        toggleActionMode(view)
    }

    private fun toggleActionMode(view: View) {
        if (selectedNoteIds.isNotEmpty()) {
            startActionMode(view)
        } else {
            exitActionMode()
        }
    }

    private fun startActionMode(noteContainer: View) {
        if(actionMode == null) {
            noteContainer.startActionMode(this)
        }
    }

    private fun exitActionMode() {
        actionMode?.finish()
    }
}
