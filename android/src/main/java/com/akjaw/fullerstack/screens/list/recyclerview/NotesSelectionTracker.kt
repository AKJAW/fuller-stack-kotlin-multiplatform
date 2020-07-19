package com.akjaw.fullerstack.screens.list.recyclerview

import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.list.DeleteNotesConfirmDialog
import model.NoteIdentifier

//TODO can this be tested?
class NotesSelectionTracker(
    private val fragmentManager: FragmentManager,
    private val onActionModeDestroyed: () -> Unit,
    private val onNoteChanged: (NoteIdentifier) -> Unit
) : ActionMode.Callback, DeleteNotesConfirmDialog.DeleteNotesConfirmationListener {

    private var actionMode: ActionMode? = null
    private var selectedNoteIdentifiers: MutableList<NoteIdentifier> = mutableListOf() //TODO preserve on save instance state

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        actionMode = mode
        val inflater = mode?.menuInflater
        inflater?.inflate(R.menu.note_list_selection, menu)
        //TODO title
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        selectedNoteIdentifiers.clear()
        actionMode = null
        onActionModeDestroyed()
        Log.d("SelectedNotes", selectedNoteIdentifiers.toString())
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete_note) {
            val dialog = DeleteNotesConfirmDialog.newInstance(selectedNoteIdentifiers)
            dialog.setPositiveClickListener(this)
            dialog.show(fragmentManager, "DeleteNotes")
        }
        return true
    }

    override fun onNotesDeleted() = exitActionMode()

    fun isSelectionModeEnabled(): Boolean = selectedNoteIdentifiers.isNotEmpty()

    fun isSelected(noteIdentifier: NoteIdentifier): Boolean = selectedNoteIdentifiers.contains(noteIdentifier)

    fun selectNote(noteIdentifier: NoteIdentifier, view: View) {
        if(selectedNoteIdentifiers.contains(noteIdentifier)){
            selectedNoteIdentifiers.remove(noteIdentifier)
        } else {
            selectedNoteIdentifiers.add(noteIdentifier)
        }
        toggleActionMode(view)
        onNoteChanged(noteIdentifier)
        Log.d("SelectedNotes", selectedNoteIdentifiers.toString())
    }

    private fun toggleActionMode(view: View) {
        if (selectedNoteIdentifiers.isNotEmpty()) {
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
