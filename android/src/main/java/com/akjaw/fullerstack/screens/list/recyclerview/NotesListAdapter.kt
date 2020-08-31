package com.akjaw.fullerstack.screens.list.recyclerview

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.soywiz.klock.DateFormat
import com.soywiz.klock.format
import model.CreationTimestamp
import model.Note

class NotesListAdapter(
    notesSelectionTrackerFactory: NotesSelectionTrackerFactory,
    initialSelectedNotes: List<CreationTimestamp>,
    private val dateFormat: DateFormat,
    private val onItemClicked: (Note) -> Unit
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    private val notesSelectionTracker = notesSelectionTrackerFactory.create(
        initialSelectedNotes = initialSelectedNotes,
        onNoteChanged = ::onNoteSelectionChanged
    )
    private var notes: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.item_notes_list, parent, false)
        return NoteViewHolder(rootView, dateFormat, notesSelectionTracker, onItemClicked)
    }

    override fun getItemCount(): Int = notes.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        val isSelected = notesSelectionTracker.isSelected(note.creationTimestamp)
        holder.bind(note, isSelected)
    }

    fun setNotes(newNotes: List<Note>) {
        val diffCallback = NotesDiffCallback(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notes = newNotes
        diffResult.dispatchUpdatesTo(this)
    }

    private fun onNoteSelectionChanged(creationTimestamp: CreationTimestamp) {
        val positionOfNote = notes.indexOfFirst { it.creationTimestamp == creationTimestamp }
        if (positionOfNote == -1) return
        notifyItemChanged(positionOfNote)
    }

    fun getSelectedNoteIds(): List<Long> = notesSelectionTracker.getSelectedNotes()

    class NoteViewHolder(
        rootView: View,
        private val dateFormat: DateFormat,
        private val selectionTracker: NotesSelectionTracker,
        private val onItemClicked: (Note) -> Unit
    ) : RecyclerView.ViewHolder(rootView) {
        val noteContainer: View = rootView.findViewById(R.id.note_container)
        val title: TextView = rootView.findViewById(R.id.note_title)
        val date: TextView = rootView.findViewById(R.id.note_date)
        val noteSyncFailed: ImageView = rootView.findViewById(R.id.note_sync_failed)

        fun bind(note: Note, isSelected: Boolean) {
            title.text = note.title
            date.text = dateFormat.format(note.creationTimestamp.unix)
            noteSyncFailed.isVisible = note.hasSyncFailed
            setBackgroundColor(isSelected)

            noteContainer.setOnClickListener {
                if (selectionTracker.isSelectionModeEnabled()) {
                    selectionTracker.select(note.creationTimestamp)
                } else {
                    onItemClicked(note)
                }
            }

            noteContainer.setOnLongClickListener {
                selectionTracker.select(note.creationTimestamp)
                true
            }
        }

        private fun setBackgroundColor(isSelected: Boolean) {
            noteContainer.background.colorFilter =
                when {
                    isSelected -> {
                        val context = noteContainer.context
                        val color = ContextCompat.getColor(context, R.color.selectedNoteBackground)
                        PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                    }
                    else -> null
                }
        }
    }
}
