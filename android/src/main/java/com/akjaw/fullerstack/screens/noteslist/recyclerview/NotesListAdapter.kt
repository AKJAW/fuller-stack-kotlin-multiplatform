package com.akjaw.fullerstack.screens.noteslist.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.screens.common.ViewMvcFactory
import data.Note

class NotesListAdapter(
    private val onNoteClickedListener: (String) -> Unit,
    private val viewMvcFactory: ViewMvcFactory
): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>(), NoteItemViewMvc.Listener {

    private var notes: List<Note> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val viewMvc = viewMvcFactory.getNoteItemViewMvc(parent)
        viewMvc.registerListener(this)

        return NoteViewHolder(viewMvc)
    }

    override fun getItemCount(): Int = notes.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.noteItemViewMvc.setTitle(note.title)
    }

    //TODO add diffutil
    fun setNotes(newNotes: List<Note>){
        notes = newNotes
        notifyDataSetChanged()
    }

    class NoteViewHolder(val noteItemViewMvc: NoteItemViewMvc): RecyclerView.ViewHolder(noteItemViewMvc.rootView)

    override fun onClicked(title: String) {
        onNoteClickedListener(title)
    }
}