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
    private val keyboardCloser: KeyboardCloser,
    private val onItemClicked: (Note) -> Unit,
    private val onSearchInputChange: (String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val notesSelectionTracker = notesSelectionTrackerFactory.create(
        initialSelectedNotes = initialSelectedNotes,
        onNoteChanged = ::onNoteSelectionChanged
    )
    private var notes: List<Note> = listOf()

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ActionRowViewHolder.VIEW_TYPE
            else -> NoteViewHolder.VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ActionRowViewHolder.VIEW_TYPE -> {
                val rootView = inflater.inflate(R.layout.item_action_row, parent, false)
                ActionRowViewHolder(keyboardCloser, onSearchInputChange, rootView)
            }
            NoteViewHolder.VIEW_TYPE -> {
                val rootView = inflater.inflate(R.layout.item_notes_list, parent, false)
                NoteViewHolder(rootView, dateFormat, notesSelectionTracker, onItemClicked)
            }
            else -> throw IllegalStateException("View type $viewType not supported")
        }
    }

    override fun getItemCount(): Int = notes.count() + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ActionRowViewHolder.VIEW_TYPE -> {
                (holder as ActionRowViewHolder).bind()
            }
            NoteViewHolder.VIEW_TYPE -> {
                val note = notes[position - 1]
                val isSelected = notesSelectionTracker.isSelected(note.creationTimestamp)
                (holder as NoteViewHolder).bind(note, isSelected)
            }
        }
    }

    fun setNotes(newNotes: List<Note>) {
        val diffCallback = NotesDiffCallback(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notes = newNotes
        diffResult.dispatchUpdatesTo(this)
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
