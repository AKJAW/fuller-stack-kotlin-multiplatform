package com.akjaw.fullerstack.screens.list.recyclerview

import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.list.DeleteNotesConfirmDialog

//TODO can this be tested?
class NotesSelectionTracker(
    private val fragmentManager: FragmentManager,
    private val onActionModeDestroyed: () -> Unit,
    private val onNoteChanged: (Int) -> Unit
) : ActionMode.Callback, DeleteNotesConfirmDialog.DeleteNotesConfirmationListener {

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
        onActionModeDestroyed()
        Log.d("SelectedNotes", selectedNoteIds.toString())
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note) {
            val dialog = DeleteNotesConfirmDialog.newInstance(selectedNoteIds)
            dialog.setPositiveClickListener(this)
            dialog.show(fragmentManager, "DeleteNotes")
        }
        return true
    }

    override fun onNotesDeleted() = exitActionMode()

    fun isSelectionModeEnabled(): Boolean = selectedNoteIds.isNotEmpty()

    fun isSelected(noteId: Int): Boolean = selectedNoteIds.contains(noteId)

    fun selectNote(noteId: Int, view: View) {
        if(selectedNoteIds.contains(noteId)){
            selectedNoteIds.remove(noteId)
        } else {
            selectedNoteIds.add(noteId)
        }
        toggleActionMode(view)
        onNoteChanged(noteId)
        Log.d("SelectedNotes", selectedNoteIds.toString())
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
