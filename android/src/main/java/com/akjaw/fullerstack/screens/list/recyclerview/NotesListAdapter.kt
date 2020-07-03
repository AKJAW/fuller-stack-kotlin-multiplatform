package com.akjaw.fullerstack.screens.list.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import model.Note

class NotesListAdapter(
    private val onItemClicked: (Note) -> Unit,
    private val viewMvcFactory: ViewMvcFactory
) : RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>(), NoteItemViewMvc.Listener {

    private var notes: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewMvc = viewMvcFactory.getNoteItemViewMvc(parent)
        viewMvc.registerListener(this)

        return NoteViewHolder(viewMvc)
    }

    override fun getItemCount(): Int = notes.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteItemViewMvc.setNote(note)
    }

    // TODO add diffutil
    fun setNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(val noteItemViewMvc: NoteItemViewMvc) : RecyclerView.ViewHolder(noteItemViewMvc.rootView)

    override fun onNoteClicked(note: Note) {
        onItemClicked(note)
    }
}
