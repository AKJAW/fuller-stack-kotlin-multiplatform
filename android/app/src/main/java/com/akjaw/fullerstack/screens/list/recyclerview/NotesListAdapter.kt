package com.akjaw.fullerstack.screens.list.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.framework.utility.KeyboardCloser
import com.akjaw.fullerstack.android.R
import com.soywiz.klock.DateFormat
import model.CreationTimestamp
import model.Note


class NotesListAdapter(
    notesSelectionTrackerFactory: NotesSelectionTrackerFactory,
    initialSelectedNotes: List<CreationTimestamp>,
    private val dateFormat: DateFormat,
    private val onItemClicked: (Note) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val notesSelectionTracker = notesSelectionTrackerFactory.create(
        initialSelectedNotes = initialSelectedNotes,
        onNoteChanged = ::onNoteSelectionChanged
    )
    private var notes: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.item_notes_list, parent, false)
        return NoteViewHolder(rootView, dateFormat, notesSelectionTracker, onItemClicked)
    }

    override fun getItemCount(): Int = notes.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val note = notes[position]
        val isSelected = notesSelectionTracker.isSelected(note.creationTimestamp)
        (holder as NoteViewHolder).bind(note, isSelected)
    }

    fun setNotes(newNotes: List<Note>, callbackOnItemsChanged: () -> Unit) {
        val diffCallback = NotesDiffCallback(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        val oldNotes = notes
        notes = newNotes
        diffResult.dispatchUpdatesTo(this)
        if (oldNotes !== newNotes) {
            callbackOnItemsChanged()
        }
    }

    private fun onNoteSelectionChanged(creationTimestamps: List<CreationTimestamp>) =
        creationTimestamps.forEach { creationTimestamp ->
            val positionOfNote = notes.indexOfFirst { it.creationTimestamp == creationTimestamp }
            if (positionOfNote > -1) {
                notifyItemChanged(positionOfNote)
            }
        }

    fun getSelectedNoteIds(): List<Long> = notesSelectionTracker.getSelectedNotes()
}
