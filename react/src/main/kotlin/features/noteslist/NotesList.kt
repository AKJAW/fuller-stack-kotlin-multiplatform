package features.noteslist

import com.ccfraser.muirwik.components.list.mList
import com.ccfraser.muirwik.components.list.mListItem
import com.ccfraser.muirwik.components.spacingUnits
import data.Note
import kotlinx.css.Display
import kotlinx.css.display
import kotlinx.css.padding
import react.RProps
import react.functionalComponent
import styled.css
import styled.styledDiv

interface NotesListProps : RProps {
    var notesList: Array<Note>
    var onNoteClicked: (note: Note) -> Unit
}

val notesList = functionalComponent<NotesListProps> { props ->
    styledDiv {
        css {
            display = Display.inlineFlex
            padding(1.spacingUnits)
        }
        mList {
            props.notesList.forEach { note ->
                mListItem(primaryText = note.toString(), onClick = { props.onNoteClicked(Note("Teee")) })
            }
        }
    }
}
