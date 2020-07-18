package com.akjaw.fullerstack.screens.list.recyclerview

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.soywiz.klock.DateFormat
import model.Note

class NotesListAdapter(
    private val onItemClicked: (Note) -> Unit,
    private val dateFormat: DateFormat
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    private var notes: List<Note> = listOf()
    private var selectedNoteIds: MutableList<Int> = mutableListOf() //TODO preserve on save instance state

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.item_notes_list, parent, false)
        return NoteViewHolder(rootView)
    }

    override fun getItemCount(): Int = notes.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        val isSelected = selectedNoteIds.contains(note.id)
        holder.apply {
            title.text = note.title
            date.text = note.creationDate.format(dateFormat)
            setBackgroundColor(isSelected)

            noteContainer.setOnClickListener {
                onItemClicked(note)
            }

            noteContainer.setOnLongClickListener {
                selectNote(
                    noteId = note.id,
                    position = position
                )
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

    private fun selectNote(noteId: Int, position: Int): Boolean {
        if(selectedNoteIds.contains(noteId)){
            selectedNoteIds.remove(noteId)
        } else {
            selectedNoteIds.add(noteId)
        }
        notifyItemChanged(position)
        return true
    }

    fun setNotes(newNotes: List<Note>) {
        val diffCallback = NotesDiffCallback(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notes = newNotes
        diffResult.dispatchUpdatesTo(this)
    }

    class NoteViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val noteContainer: View = rootView.findViewById(R.id.note_container)
        val title: TextView = rootView.findViewById(R.id.note_title)
        val date: TextView = rootView.findViewById(R.id.note_date)
    }
}
