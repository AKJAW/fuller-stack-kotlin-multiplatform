package com.akjaw.fullerstack.screens.noteslist.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc

class NoteItemViewMvc(layoutInflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableViewMvc<NoteItemViewMvc.Listener>() {

    interface Listener {
        fun onNoteClicked(title: String)
    }

    override val rootView: View = layoutInflater.inflate(R.layout.item_notes_list, parent, false)
    private val title: TextView = findViewById(R.id.note_title)
    private val date: TextView = findViewById(R.id.note_date)

    init {
        rootView.setOnClickListener {
            listeners.forEach { listener ->
                listener.onNoteClicked(title.text.toString())
            }
        }
    }

    fun setTitle(titleText: String) {
        title.text = titleText
    }

    fun setDate(dateText: String){
        date.text = dateText
    }
}
