package com.akjaw.fullerstack.screens.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.akjaw.fullerstack.android.R
import com.akjaw.fullerstack.screens.common.base.BaseObservableViewMvc

class NotesListViewMvc(inflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableViewMvc<NotesListViewMvc.Listener>() {

    interface Listener {
        fun onButtonClicked()
    }

    override val rootView: View = inflater.inflate(R.layout.layout_notes_list, parent, false)
    private val textView: TextView = rootView.findViewById(R.id.text)
    private val button: Button = rootView.findViewById(R.id.button)

    init {
        button.setOnClickListener {
            listeners.forEach {
                it.onButtonClicked()
            }
        }
    }

    fun setText(newText: String) {
        textView.text = newText
    }
}
