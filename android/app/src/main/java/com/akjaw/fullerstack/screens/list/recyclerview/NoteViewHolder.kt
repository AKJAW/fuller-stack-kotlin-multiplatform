package com.akjaw.fullerstack.screens.list.recyclerview

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.akjaw.fullerstack.android.R
import com.soywiz.klock.DateFormat
import com.soywiz.klock.format
import model.Note

class NoteViewHolder(
    rootView: View,
    private val dateFormat: DateFormat,
    private val selectionTracker: NotesSelectionTracker,
    private val onItemClicked: (Note) -> Unit
) : RecyclerView.ViewHolder(rootView) {

    companion object {
        const val VIEW_TYPE = 1
    }

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
