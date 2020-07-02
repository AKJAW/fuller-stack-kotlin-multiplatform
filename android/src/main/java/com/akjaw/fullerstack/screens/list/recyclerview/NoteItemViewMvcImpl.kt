package com.akjaw.fullerstack.screens.list.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.akjaw.fullerstack.android.R
import com.soywiz.klock.DateFormat
import data.Note

class NoteItemViewMvcImpl(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?,
    private val dateFormat: DateFormat
) : NoteItemViewMvc() {
    override val rootView: View = layoutInflater.inflate(R.layout.item_notes_list, parent, false)

    private val noteContainer: View = findViewById(R.id.note_container)
    private lateinit var note: Note
    private val title: TextView = findViewById(R.id.note_title)
    private val date: TextView = findViewById(R.id.note_date)
    init {
        noteContainer.setOnClickListener {
            listeners.forEach { listener ->
                listener.onNoteClicked(note)
            }
        }
    }

    override fun setNote(note: Note) {
        this.note = note
        title.text = note.title
        date.text = note.creationDate.format(dateFormat)
    }
}
