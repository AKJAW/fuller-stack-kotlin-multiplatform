package com.akjaw.fullerstack.screens.noteslist.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.akjaw.fullerstack.android.R

class NoteItemViewMvcImpl(layoutInflater: LayoutInflater, parent: ViewGroup?) : NoteItemViewMvc() {

    override val rootView: View = layoutInflater.inflate(R.layout.item_notes_list, parent, false)
    private val noteContainer: View = findViewById(R.id.note_container)
    private val title: TextView = findViewById(R.id.note_title)
    private val date: TextView = findViewById(R.id.note_date)

    init {
        noteContainer.setOnClickListener {
            listeners.forEach { listener ->
                listener.onNoteClicked(title.text.toString())
            }
        }
    }

    override fun setTitle(titleText: String) {
        title.text = titleText
    }

    override fun setDate(dateText: String){
        date.text = dateText
    }

}
