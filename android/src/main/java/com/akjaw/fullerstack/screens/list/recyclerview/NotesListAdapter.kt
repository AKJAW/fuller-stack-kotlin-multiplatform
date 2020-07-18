package com.akjaw.fullerstack.screens.list.recyclerview

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.list.DeleteNoteConfirmDialog
import com.soywiz.klock.DateFormat
import model.Note

class NotesListAdapter(
    private val fragmentManager: FragmentManager,
    private val onItemClicked: (Note) -> Unit,
    private val dateFormat: DateFormat
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    private val notesSelectionTracker = NotesSelectionTracker(::onDestroyActionMode, ::onDeleteClicked)
    private var notes: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.item_notes_list, parent, false)
        return NoteViewHolder(rootView)
    }

    override fun getItemCount(): Int = notes.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        val isSelected = notesSelectionTracker.isSelected(note.id)
        holder.apply {
            title.text = note.title
            date.text = note.creationDate.format(dateFormat)
            setBackgroundColor(isSelected)

            noteContainer.setOnClickListener {
                if(notesSelectionTracker.isSelectionModeEnabled()){
                    selectNote(note, position)
                } else {
                    onItemClicked(note)
                }
            }

            noteContainer.setOnLongClickListener {
                selectNote(note, position)
                true
            }
        }
    }

    private fun NoteViewHolder.setBackgroundColor(isSelected: Boolean) {
        noteContainer.background.colorFilter = when {
            isSelected -> {
                val context = noteContainer.context
                val color = ContextCompat.getColor(context, R.color.selectedNoteBackground)
                PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
            }
            else -> null
        }
    }

    private fun NoteViewHolder.selectNote(note: Note, position: Int) {
        notesSelectionTracker.selectNote(note.id, itemView)
        notifyItemChanged(position)
    }

    fun setNotes(newNotes: List<Note>) {
        val diffCallback = NotesDiffCallback(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notes = newNotes
        diffResult.dispatchUpdatesTo(this)
    }

    private fun onDestroyActionMode() = notifyDataSetChanged()

    private fun onDeleteClicked(noteIds: List<Int>) {
        DeleteNoteConfirmDialog().show(fragmentManager, "DeleteNotes")
    }

    class NoteViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val noteContainer: View = rootView.findViewById(R.id.note_container)
        val title: TextView = rootView.findViewById(R.id.note_title)
        val date: TextView = rootView.findViewById(R.id.note_date)
    }
}
