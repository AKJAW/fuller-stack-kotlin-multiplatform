package com.akjaw.fullerstack.screens.list.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.soywiz.klock.DateFormat
import model.Note

class NotesListAdapter(
    private val onItemClicked: (Note) -> Unit,
    private val dateFormat: DateFormat
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    private var notes: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val rootView = inflater.inflate(R.layout.item_notes_list, parent, false)
        return NoteViewHolder(rootView)
    }

    override fun getItemCount(): Int = notes.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.apply {
            title.text = note.title
            date.text = note.creationDate.format(dateFormat)

            noteContainer.setOnClickListener {
                onItemClicked(note)
            }
        }
    }

    // TODO add diffutil
    fun setNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val noteContainer: View = rootView.findViewById(R.id.note_container)
        val title: TextView = rootView.findViewById(R.id.note_title)
        val date: TextView = rootView.findViewById(R.id.note_date)
    }
}
